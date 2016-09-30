package org.framework.utils;

import java.util.List;

import org.framework.maincontroller.MainController;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FindLocators {

	WebDriver driver;

	public FindLocators(WebDriver driver)
	{
		this.driver = driver;
	}


	public WebElement findElementByXPath(WebDriver driver,String locator)
	{

		return driver.findElement(By.xpath(locator));
	}
	
	public WebElement findElementByClassName(WebDriver driver,String locator)
	{
		return driver.findElement(By.className(locator));
	}

	
	public WebElement findElementById(WebDriver driver,String locator)
	{
		return driver.findElement(By.id(locator));
	}
	
	
	public WebElement findElementByLinkText(WebDriver driver,String locator)
	{
		return driver.findElement(By.linkText(locator));
	}
	
	
	public WebElement findElementByCssSelector(WebDriver driver,String locator)
	{
		return driver.findElement(By.cssSelector(locator));
	}
	
	
	public WebElement findElementByName(WebDriver driver,String locator)
	{
		return driver.findElement(By.name(locator));
	}
	
	public WebElement findElementByPartialLinkText(WebDriver driver,String locator)
	{
		return driver.findElement(By.partialLinkText(locator));
	}
	
	public WebElement findElementByTagName(WebDriver driver,String locator)
	{
		return driver.findElement(By.tagName(locator));
	}
	
	
	public List<WebElement> findElementsByXPath(WebDriver driver,String locator)
	{
		return driver.findElements(By.xpath(locator));
	}
	
	public List<WebElement> findElementsByClassName(WebDriver driver,String locator)
	{
		return driver.findElements(By.className(locator));
	}
	
	
	public List<WebElement> findElementsById(WebDriver driver,String locator)
	{
		return driver.findElements(By.id(locator));
	}
	
	
	public List<WebElement> findElementsByLinkText(WebDriver driver,String locator)
	{
		return driver.findElements(By.linkText(locator));
	}
	
	
	public List<WebElement> findElementsByCssSelector(WebDriver driver,String locator)
	{
		return driver.findElements(By.cssSelector(locator));
	}
	
	
	public List<WebElement> findElementsByName(WebDriver driver,String locator)
	{
		return driver.findElements(By.name(locator));
	}
	
	public List<WebElement> findElementsByPartialLinkText(WebDriver driver,String locator)
	{
		return driver.findElements(By.partialLinkText(locator));
	}
	
	public List<WebElement> findElementsByTagName(WebDriver driver,String locator)
	{
		return driver.findElements(By.tagName(locator));
	}
	
	
}
