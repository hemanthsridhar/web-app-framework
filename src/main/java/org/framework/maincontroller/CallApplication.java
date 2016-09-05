package org.framework.maincontroller;
import org.framework.maincontroller.MainController;
import org.framework.utils.ApplicationSetUpPropertyFile;
import org.testng.annotations.BeforeMethod;

public class CallApplication extends MainController{

	ApplicationSetUpPropertyFile setUp = new ApplicationSetUpPropertyFile();
	
	@BeforeMethod(alwaysRun=true)
	public void callApp() {
		getDriver().get(setUp.getUrl());
		getDriver().manage().window().maximize();
	}
}
