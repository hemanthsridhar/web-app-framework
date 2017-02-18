package org.framework.maincontroller;
import org.framework.maincontroller.MainController;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import ru.yandex.qatools.allure.annotations.Step;

public class CallApplication extends MainController{
	
	@Parameters(value={"Product Name","Application URL","browser","Remote Url"})
	@BeforeMethod(alwaysRun=true)
	public void callApp(String productName,String applicationURL,String browser,String remoteURL) {
		openApplication(applicationURL);
		maximize();
	}
	
	@Step("Navigate to {0}")
	public void openApplication(String applicationURL){
		getDriver().get(applicationURL);
	}
	
	@Step("Maximize the browser window")
	public void maximize(){
		getDriver().manage().window().maximize();
	}
}
