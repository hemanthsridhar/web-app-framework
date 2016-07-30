package org.framework.utils;

import java.util.List;

import org.framework.maincontroller.MainController;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waiting extends MainController {

	public   void explicitWaitElementToBeClickable(WebElement element, int time){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public   void explicitWaitElementToBeClickable(By element, int time){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public   void explicitWaitElementToBeSelected(WebElement element, int time){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.elementToBeSelected(element));
	}
	
	public   void explicitWaitElementToBeSelected(By element, int time){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.elementToBeSelected(element));
	}
	
	public   void explicitWaitTextToBePresentInElement(WebElement element, int time,String text){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.textToBePresentInElement(element, text));
	}
	
	
	public   void explicitWaitTitleContains(WebElement element, int time,String title){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.titleContains(title));
	}
	
	public   void explicitWaitTitleContains(By element, int time,String title){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.titleContains(title));
	}
	
	
	public   void explicitWaitTitleIs(WebElement element, int time,String title){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.titleIs(title));
	}
	

	public   void explicitWaitTitleIs(By element, int time,String title){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.titleIs(title));
	}
	
	public   void explicitWaitVisibilityOfElement(WebElement element, int time){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.visibilityOf(element));
	}
	
	public   void explicitWaitVisibilityOfElement(By element, int time){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.visibilityOfElementLocated(element));
	}
	
	public   void explicitWaitSelectionStateToBe(WebElement element, int time,boolean selected){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.elementSelectionStateToBe(element, selected));
	}
	
	
	public   void explicitWaitSelectionStateToBe(By element, int time,boolean selected){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.elementSelectionStateToBe(element, selected));
	}
	
	public   void explicitWaitForAlert(int time){
		new WebDriverWait(getDriver(), time).until(ExpectedConditions.alertIsPresent());
	}

	public   void explicitWaitVisibilityOfElements(List<WebElement> element, int time) {
		
		new WebDriverWait(getDriver(),time).until(ExpectedConditions.visibilityOfAllElements(element));
	}
	
public   void explicitWaitVisibilityOfElements(By element, int time) {
		
		new WebDriverWait(getDriver(),time).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
	}
	
}
