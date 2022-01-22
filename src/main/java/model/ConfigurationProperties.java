package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;


public class ConfigurationProperties {
	
//	private static org.apache.log4j.Logger logger = Logger.getLogger(ConfigurationProperties.class);
	
	BufferedWriter bw = null;
	FileWriter fw = null;
	
	public String configurationReader(String value,String address){
		Properties prop = new Properties();
		try {
			prop.load(getClass().getResourceAsStream("/configuration/rahifashion.properties"));
		} catch (IOException e) {
			e.printStackTrace();
//			logger.error("ERROR",e);
		}
		return prop.getProperty(value);
		
	}
	
//	public static void configurationWriter(String name,String value,String address) throws URISyntaxException{
//		Properties prop = new Properties();
//		OutputStream output = null;
//		
//		URL resourceUrl = ConfigurationProperties.class.getResource(address);
//		System.out.println(address);
//		System.out.println(resourceUrl);
//		File file = new File(resourceUrl.toURI());
//
//		try {
//			output = new FileOutputStream(file);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		prop.setProperty(name, value);
//		try {
//			prop.store(output,null);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}

