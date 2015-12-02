package com.perfectomator.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.remote.RemoteWebDriver;

/*
 * Execute touch on random coordinates
 */
public class Tap extends Event {
	Random rand = new Random();
	
	public Tap(RemoteWebDriver driver) {
		super(driver);
	}

	@Override
	public boolean execute() {
		int width = rand.nextInt(100);
		int height = rand.nextInt(100);
		
		Map<String, Object> params = new HashMap<>();
		params.put("location", "" + width +"%," + height + "%");
		_driver.executeScript("mobile:touch:tap", params);

		return true;
	}

}
