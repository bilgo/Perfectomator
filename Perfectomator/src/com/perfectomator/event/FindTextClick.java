package com.perfectomator.event;

import org.openqa.selenium.remote.RemoteWebDriver;


public class FindTextClick extends FindElementClick {

	public FindTextClick(RemoteWebDriver driver) {
		super(driver);
	}

	@Override
	protected String getTag() {
		return "text";
	}


}
