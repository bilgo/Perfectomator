package com.perfectomator;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomator.event.Back;
import com.perfectomator.event.Event;
import com.perfectomator.event.EventType;
import com.perfectomator.event.FindButtonClick;
import com.perfectomator.event.FindImageClick;
import com.perfectomator.event.FindTextClick;
import com.perfectomator.event.Home;
import com.perfectomator.event.Rotate;
import com.perfectomator.event.StartApp;
import com.perfectomator.event.Tap;
import com.perfectomator.utils.Utils;
import com.perfectomobile.selenium.util.EclipseConnector;

public class PerfectomatorMain implements Runnable  {
	
	/** This class holds all the relevant parameters for running the script
	 *  It is must to fill all these fields */
	public static class ScriptParameters{
		//Write all the device IDs here.
		public static final String[] deviceIDs = {"0123456",	"6543210"};
		// the tested app. this app should be installed on the device
		public static final String appName = "myApp"; 
		// the device cloud 
		public static final String host = "???.perfectomobile.com";
		// your credential to the device cloud
		public static final String user = "???";
		public static final String password = "???";
		
	}

	/********************************** Beginning of the Script *************************************/	
	private String _deviceID ;
	
	
	public PerfectomatorMain(String deviceId){
		_deviceID = deviceId;
	}
	
	/** raise a thread pool to run the script across multiple devices */
	public static void main(String[] args) throws MalformedURLException, IOException {
        ExecutorService executor = Executors.newFixedThreadPool(ScriptParameters.deviceIDs.length);
        for (String deviceId : ScriptParameters.deviceIDs) {
        	PerfectomatorMain test = new PerfectomatorMain(deviceId);
               executor.execute(test);
        }      
        executor.shutdown();
	}
	
	 @Override
     public void run() {
		System.out.println("Run started");
		String browserName = "";
		DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);											
		capabilities.setCapability("user", ScriptParameters.user);						
		capabilities.setCapability("password", ScriptParameters.password);					
		capabilities.setCapability("deviceName", _deviceID);
		
		// Use the automationName capability to define the required framework - Appium (this is the default) or PerfectoMobile.
		capabilities.setCapability("automationName", "PerfectoMobile");
		
	
		RemoteWebDriver driver = null;
		try {
			setExecutionIdCapability(capabilities);
			
			
	        driver = new RemoteWebDriver(new URL("https://" + ScriptParameters.host + "/nexperience/perfectomobile/wd/hub"), capabilities);
	        
			for (int i=0; i<5; i++){
				
				Utils.startApp(ScriptParameters.appName, driver);
						
				Utils.sleep(5000);
				
				//Monkey testing
				try{
					monkeyTesting(driver);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				
				
				driver.close();
				
				// In case you want to down the report or the report attachments, do it here.
				// RemoteWebDriverUtils.downloadReport(driver, "pdf", "C:\\test\\report");
				// RemoteWebDriverUtils.downloadAttachment(driver, "video", "C:\\test\\report\\video", "flv");
				// RemoteWebDriverUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
			driver.quit();
		}
		
		System.out.println("Run ended");
	}
	
	
	private static void monkeyTesting(RemoteWebDriver driver) {
		
		//Give chances for each event
		Map<EventType,Integer> chances = new HashMap<EventType,Integer>();
		chances.put(EventType.FIND_BUTTON_CLICK, 50);
		chances.put(EventType.FIND_TEXT_CLICK, 10);
		chances.put(EventType.FIND_IMAGE_CLICK, 10);
		chances.put(EventType.ROTATE, 10);
		chances.put(EventType.HOME, 5);
		chances.put(EventType.BACK, 5);
		chances.put(EventType.TAP, 50);
		chances.put(EventType.START_APP, 25);
		
		//How many times do you want to random events and execute?
		for (int i=0; i<1; i++){
			
			//Random events
			ArrayList<Event> events = randomEvents(50,chances,driver);
			
			//Release the monkey
			goCrazy(events,driver);
		}
	}

	private static void goCrazy(ArrayList<Event> events, RemoteWebDriver driver) {
		Iterator<Event> it = events.iterator();
		
		//Run the monkey testing. After each test check if the app on front.
		while (it.hasNext()){
			Event event = it.next();
			event.execute();
			
			String command = "mobile:application:info";
			Map<String, Object> params = new HashMap<>();
			params.put("property", "frontapp");
			String frontapp = (String) driver.executeScript(command, params);
			
			if (!frontapp.equalsIgnoreCase(ScriptParameters.appName)){
				Utils.startApp(ScriptParameters.appName, driver);
				
				Utils.sleep(1000);
			}
		}
	}


	public static ArrayList<Event> randomEvents(int eventsNum, Map<EventType,Integer> eventChances, RemoteWebDriver driver) {
		ArrayList<EventType> arr = initChances(eventChances);
		ArrayList<Event> events = new ArrayList<Event>();
		
		for (int i=0; i<eventsNum; i++){
			Event event = randomEvent(arr,driver);
			events.add(event);
		}
		
		return events;
	}

	private static ArrayList<EventType> initChances(Map<EventType,Integer> eventChances){
		ArrayList<EventType> arr = new ArrayList<EventType>();
		for (EventType eventType : eventChances.keySet()){
			Integer chances = eventChances.get(eventType);
			
			for (int i=0; i<chances; i++){
				arr.add(eventType);
			}
		}
		
		return arr;
	}
	
	private static Event randomEvent(ArrayList<EventType> arr, RemoteWebDriver driver) {
		
		int length = arr.size();
		
		Random rand = new Random();
		int eventRand = rand.nextInt(length);
		
		EventType eventType = arr.get(eventRand);
		
		Event event = null;
		switch(eventType){
		case ROTATE:

			event = new Rotate(driver);
			break;
		case HOME:
			event = new Home(driver,ScriptParameters.appName);
			break;
		case FIND_BUTTON_CLICK:
			event = new FindButtonClick(driver);
			break;
		case FIND_TEXT_CLICK:
			event = new FindTextClick(driver);
			break;
		case FIND_IMAGE_CLICK:
			event = new FindImageClick(driver);
			break;
		case BACK:
			event = new Back(driver);
			break;
		case TAP:
			event = new Tap(driver);
			break;
		case START_APP:
			event = new StartApp(driver,ScriptParameters.appName);
			break;
		}
		
		return event;
	}
	


		
 	private static void setExecutionIdCapability(DesiredCapabilities capabilities) throws IOException {
		EclipseConnector connector = new EclipseConnector();
		String executionId = connector.getExecutionId();
		capabilities.setCapability(EclipseConnector.ECLIPSE_EXECUTION_ID, executionId);
	}
}
