package com.perfectomator.event;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomator.utils.Utils;

public class Home extends Event {

	private String _appName;

	public Home(RemoteWebDriver driver,String appName) {
		super(driver);
		_appName = appName;
	}

	@Override
	public boolean execute() {
		Map<String, Object> params = new HashMap<>();
		params = new HashMap<>();
		params.put("keySequence", "HOME");
		_driver.executeScript("mobile:presskey", params);
		
		Utils.sleep(1000);
		
		Utils.startApp(_appName, _driver);
		
		Utils.sleep(1000);
		
		return true;
	}

}
