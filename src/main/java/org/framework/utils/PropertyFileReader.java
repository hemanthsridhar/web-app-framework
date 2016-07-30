package org.framework.utils;

import java.io.FileReader;
import java.util.Properties;

public class PropertyFileReader {

	public String propertiesReader(String filePath,String key)
	{
		try
		{
		FileReader reader = new FileReader(filePath);
		Properties properties = new Properties();
		properties.load(reader);
		String s =  properties.getProperty(key);
		return s;	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
