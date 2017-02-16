package org.framework.maincontroller;
import org.framework.maincontroller.MainController;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class CallApplication extends MainController{
	
	@Parameters(value={"Product Name","Application URL","browser","Remote Url"})
	@BeforeMethod(alwaysRun=true)
	public void callApp(String productName,String applicationURL,String browser,String remoteURL) {
		getDriver().get(applicationURL);
		getDriver().manage().window().maximize();
	}
}
