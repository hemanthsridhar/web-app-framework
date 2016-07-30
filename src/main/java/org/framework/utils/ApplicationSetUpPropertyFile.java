package org.framework.utils;

import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

@Resource.File("resources/ApplicationSetUp.properties")
public class ApplicationSetUpPropertyFile {


	public ApplicationSetUpPropertyFile() {
		PropertyLoader.populate(this);
	}

	@Property("url")
    private String url;

	
	@Property("productName")
    private String productName;
	

	public String getUrl() {
		return url;
	}
	
	public String getProductName() {
		return productName;
	}
}
