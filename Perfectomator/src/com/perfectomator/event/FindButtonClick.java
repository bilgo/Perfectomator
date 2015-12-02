package com.perfectomator.event;

import org.openqa.selenium.remote.RemoteWebDriver;

public class FindButtonClick extends FindElementClick {

	public FindButtonClick(RemoteWebDriver driver) {
		super(driver);
	}

	@Override
	protected String getTag() {
		return "button";
	}


}
