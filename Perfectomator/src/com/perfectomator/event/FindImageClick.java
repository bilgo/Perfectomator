package com.perfectomator.event;

import org.openqa.selenium.remote.RemoteWebDriver;

public class FindImageClick extends FindElementClick {

	public FindImageClick(RemoteWebDriver driver) {
		super(driver);
	}

	@Override
	protected String getTag() {
		return "image";
	}


}
