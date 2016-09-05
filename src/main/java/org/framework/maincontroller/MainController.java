package org.framework.maincontroller;



import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import ru.yandex.qatools.allure.annotations.Attachment;

public class MainController implements  IHookable{

	private static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();

	@Parameters(value={"browser","Node Url"})
	@BeforeMethod(alwaysRun=true)
	public void setUp(String browser,String url) throws MalformedURLException {
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setBrowserName(browser);
		dc.setJavascriptEnabled(true);
		driver.set(new RemoteWebDriver(new URL(url), dc));
	}

	public WebDriver getDriver() {
		return driver.get();
	}


@Override
public void run(IHookCallBack callBack, ITestResult testResult){
	callBack.runTestMethod(testResult);
    if (testResult.getThrowable()!= null) {
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	try
    	{
    	saveScreenshot(testResult.getName(),getDriver());
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
   }
}


@Attachment(value = "Screenshot of {0}", type = "image/png")
  public byte[] saveScreenshot(String name,WebDriver driver) {
	return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
  }

	@AfterMethod(alwaysRun=true)
	public void closeBrowser() {
		getDriver().quit();
	}
}
