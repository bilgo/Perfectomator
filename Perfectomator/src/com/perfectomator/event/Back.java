package com.perfectomator.event;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.RemoteWebDriver;

public class Back extends Event {

	public Back(RemoteWebDriver driver) {
		super(driver);
	}

	@Override
	public boolean execute() {
		Map<String, Object> params = new HashMap<>();
		params = new HashMap<>();
		params.put("keySequence", "BACK");
		_driver.executeScript("mobile:presskey", params);

		
		return true;
	}

}
