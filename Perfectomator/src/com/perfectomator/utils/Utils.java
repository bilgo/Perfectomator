package com.perfectomator.utils;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Utils {
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
	
	public static void startApp(String appName, RemoteWebDriver remoteWebDriver){
		String command = "mobile:application:open";
		Map<String, Object> params = new HashMap<>();
		params.put("name", appName);
		Object result = remoteWebDriver.executeScript(command, params);
		System.out.println("**** Start app ended with result: "+result);
	}
	
	private static void closeApp(String appName, RemoteWebDriver remoteWebDriver){
		String command = "mobile:application:close";
		Map<String, Object> params = new HashMap<>();
		params.put("name", appName);
		Object result = remoteWebDriver.executeScript(command, params);
		System.out.println("**** close app ended with result: "+result);
	}
	
	
	private static void cleanApplications(RemoteWebDriver remoteWebDriver){
		String command = "mobile:application:reset";
		Object result = remoteWebDriver.executeScript(command);
		System.out.println("**** clean app ended with result: "+result);
	}
	
	private static void uninstallApp(String appName, RemoteWebDriver remoteWebDriver){
		String command = "mobile:application:uninstall";
		Map<String, Object> params = new HashMap<>();
		params.put("name", appName);
		Object result = remoteWebDriver.executeScript(command, params);
		System.out.println("**** uninstall app ended with result: "+result);
	}
	private static void switchToContext(RemoteWebDriver driver, String context) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		Map<String,String> params = new HashMap<String,String>();
		params.put("name", context);
		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	}
}
