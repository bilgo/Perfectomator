package com.perfectomator.event;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class FindElementClick extends Event {

	Random rand = new Random();
	
	public FindElementClick(RemoteWebDriver driver) {
		super(driver);
	}

	@Override
	public boolean execute() {
		_driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
		List<WebElement> elements = _driver.findElements(By.xpath("//" + getTag() +"[@hidden=\"false\"]"));
		
		_driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		
		int size = elements.size();
		
		if (size ==0) return true;
		
		
		int index = rand.nextInt(size);
		
		WebElement button = elements.get(index);
		try{
			button.click();
		}catch(Exception e){
			
		}
		
		
		return true;
	}

	protected abstract String getTag();

}
