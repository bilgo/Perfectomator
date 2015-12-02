package com.perfectomator.event;

import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class Event {
	protected RemoteWebDriver _driver;
	
	public Event(RemoteWebDriver driver){
		_driver = driver;
	}
	
	public abstract boolean execute();
}
