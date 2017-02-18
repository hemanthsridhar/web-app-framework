package org.framework.maincontroller;



import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import ru.yandex.qatools.allure.annotations.Attachment;

public class MainController implements  IHookable{

	public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();
	
	@Parameters(value={"Product Name","Application URL","browser","Remote Url"})
	@BeforeMethod(alwaysRun=true)
	public void setUp(String productName,String applicationURL,String browser,String remoteUrl) throws MalformedURLException {
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setBrowserName(browser);
		dc.setJavascriptEnabled(true);
		driver.set(new RemoteWebDriver(new URL(remoteUrl), dc));
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
	
	
	@Attachment(value = "Video of {0}",type="video/mp4")
	public byte[] saveVideo(String name, WebDriver driver) throws Exception {
		return getFile("Videos/"+name+".mp4");
		
	}
	
	public byte[] getFile(String fileName) throws Exception {
		File file = new File(fileName);
		return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	   
	}
	
	@Attachment(value = "json {0} attachment", type = "text/json")
	public byte[] attachJSONFile(String attachmentName,String file) {
	    return file.getBytes();
	}

	@Attachment(value = "xlsx {0} attachment")
	public byte[] attachXLSXFile(String attachmentName,String file) {
	    return file.getBytes();
	}

	@Attachment(value = "xml {0} attachment", type = "text/xml")
	public byte[] attachXMLFile(String attachmentName,String file) {
	    return file.getBytes();
	}

	@Attachment(value = "text {0} attachment", type = "text/plain")
	public byte[] attachTextFile(String attachmentName,String file) {
	    return file.getBytes();
	}


	@Attachment(value = "csv {0} attachment", type = "text/csv")
	public byte[] attachCSVFile(String attachmentName, String file) {
	    return file.getBytes();
	}


	@Attachment(value = "csv attachment", type = "text/csv")
	public byte[] saveCsvAttachment(String filePath) throws Exception {
	    return getFile(filePath);
	}

	@Attachment(value = "xml attachment", type = "text/xml")
	public byte[] saveXMLAttachment(String filePath) throws Exception {
	    return getFile(filePath);
	}


	@Attachment(value = "xlsx attachment")
	public byte[] saveXlsxAttachment(String filePath) throws Exception {
	    return getFile(filePath);
	}

	@Attachment(value = "text attachment", type ="text/plain")
	public byte[] saveTextFileAttachment(String filePath) throws Exception {
	    return getFile(filePath);
	}

	@Attachment(value="JSON attachment", type="text/json")
	public byte[] saveJSONFileAttachment(String filePath) throws Exception
	{
		return getFile(filePath);
	}
	
	public void attachFile(String file,String attachmentName,String format) throws Exception
	{
	    if(format.equals("xlsx"))
	    {
	        attachXLSXFile(attachmentName,file);
	    }
	    else if(format.equals("xml"))
	    {
	        attachXMLFile(attachmentName,file);
	    }
	    else if(format.equals("txt"))
	    {
	        attachTextFile(attachmentName,file);
	    }
	    else if(format.equals("csv"))
	    {
	        attachCSVFile(attachmentName,file);
	    }
	    else if (format.equals("json"))
	    {
	        attachJSONFile(attachmentName,file);
	    }
	}


}
