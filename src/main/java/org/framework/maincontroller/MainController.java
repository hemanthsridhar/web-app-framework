package org.framework.maincontroller;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.framework.utils.PermittedCharacters;
import org.framework.utils.PropertyFileReader;
import org.framework.utils.RandomGenerator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.HttpCommandExecutor;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class MainController implements IHookable {
	
	public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();
	
	@BeforeSuite(alwaysRun=true)
	public void cleanTempFolders() throws Exception{
		PropertyFileReader property = new PropertyFileReader();
		if(property.propertiesReader("MediaConfig.properties", "fullScreenshotEnable").equals("true"))
    	{
			File fullScreenshotTempPath = new File(property.propertiesReader("MediaConfig.properties", "screenshotFolderName"));
			FileUtils.cleanDirectory(fullScreenshotTempPath.getAbsoluteFile());
    	}
	}
	
	@Parameters(value={"Product Name","Application URL","Platform","browser","Remote Url"})
	@BeforeMethod(alwaysRun=true)
	public void setUp(String productName,String applicationURL,String platform,String browser,String remoteUrl) throws Exception {
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setBrowserName(browser);
		dc.setJavascriptEnabled(true);
		switch(platform)
		{
			case "ANY":dc.setPlatform(Platform.ANY);
			break;
			case "EL_CAPITAN":dc.setPlatform(Platform.EL_CAPITAN);
			break;
			case "LINUX":dc.setPlatform(Platform.LINUX);
			break;
			case "MAC":dc.setPlatform(Platform.MAC);
			break;
			case "MAVERICKS":dc.setPlatform(Platform.MAVERICKS);
			break;
			case "MOUNTAIN_LION":dc.setPlatform(Platform.MOUNTAIN_LION);
			break;
			case "SNOW_LEOPARD":dc.setPlatform(Platform.SNOW_LEOPARD);
			break;
			case "UNIX":dc.setPlatform(Platform.UNIX);
			break;
			case "VISTA":dc.setPlatform(Platform.VISTA);
			break;
			case "WIN10":dc.setPlatform(Platform.WIN10);
			break;
			case "WIN8":dc.setPlatform(Platform.WIN8);
			break;
			case "WIN8_1":dc.setPlatform(Platform.WIN8_1);
			break;
			case "WINDOWS":dc.setPlatform(Platform.WINDOWS);
			break;
			case "XP":dc.setPlatform(Platform.XP);
			break;
			case "YOSEMITE":dc.setPlatform(Platform.YOSEMITE);
			break;
			case "ANDROID":dc.setPlatform(Platform.ANDROID);
			break;
			default: throw new Exception("Invalid Platform name");
		}
		driver.set(new RemoteWebDriver(new URL(remoteUrl), dc));
	}

	public WebDriver getDriver() {
		return driver.get();
	}
	
public void run(IHookCallBack callBack, ITestResult testResult){
	callBack.runTestMethod(testResult);
    if (testResult.getThrowable()!= null) {
    	PropertyFileReader property = new PropertyFileReader();
    	if(property.propertiesReader("MediaConfig.properties", "fullScreenshotEnable").equals("true"))
    	{
    		String screenshotName=new RandomGenerator().random(10, PermittedCharacters.ALPHANUMERIC); 
    		try {
				captureScreenshot(screenshotName,testResult.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	else
    	{
    		saveScreenshot(testResult.getName(),getDriver());
    	}
    }
    SessionId sessionId = ((RemoteWebDriver) getDriver()).getSessionId();
    getDriver().quit();
    URL remoteServer = ((HttpCommandExecutor)((RemoteWebDriver) getDriver()).getCommandExecutor()).getAddressOfRemoteServer();
	try {
		saveVideo(testResult.getName(),remoteServer,getDriver(),sessionId);
	} catch (Exception e) {
	}
}

public void captureScreenshot(String screenshotTempName,String testCaseName) throws Exception {
	Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(getDriver());
	PropertyFileReader property = new PropertyFileReader();
	String screenshotBaseFolderPath = property.propertiesReader("MediaConfig.properties", "screenshotFolderName");
	String pathToTheScreenshot = screenshotBaseFolderPath+"/" + screenshotTempName.trim() + ".png";
	File file = new File(pathToTheScreenshot);
	if (!file.exists()) {
		file.mkdir();
		file.createNewFile();
	}
	ImageIO.write(screenshot.getImage(), "png", new File(pathToTheScreenshot));
	embedFullScreenshotToAllure(pathToTheScreenshot,testCaseName);
}

@Attachment(value = "Full Screenshot of {0}", type = "image/png")
public byte[] embedFullScreenshotToAllure(String screenshotName,String screenshotPath) throws Exception {
	File file = new File(screenshotPath);
	return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	
}

public void saveVideo(String testCaseName,URL remoteServer, WebDriver driver,SessionId sessionId) throws Exception {
	URL videoUrl = new URL(remoteServer, "/grid/admin/HubVideoInfoServlet/?sessionId=" + sessionId);
	Response response = RestAssured.given().when().get(videoUrl);
	String pathOfTheFile = JsonPath.read(response.asString(), "$.additional.path");
	attachVideo(testCaseName,pathOfTheFile);
}

@Attachment(value = "Screenshot of {0}", type = "image/png")
  public byte[] saveScreenshot(String name,WebDriver driver) {
	return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
  }

	
	@Attachment(value = "Video of {0}",type="video/mp4")
	public byte[] saveVideoMP4(String testCaseName,String path, WebDriver driver) throws Exception {
		return getFile(path+".mp4");
		
	}
	
	@Attachment(value = "Video of {0}",type="video/webm")
	public byte[] attachVideo(String testCaseName,String path) throws Exception {
		return getFile(path);
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
