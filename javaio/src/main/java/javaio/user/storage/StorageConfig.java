package javaio.user.storage;

import java.io.IOException;
import java.util.Properties;

public class StorageConfig {
	
	final String name = "/config.properties";
	
	Properties properties = new Properties();
	
	public StorageConfig() {
		try {
			properties.load(StorageConfig.class.getResourceAsStream(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		StorageConfig storageConfig = new StorageConfig();
		
		String property = storageConfig.properties.getProperty("storage.basepath");
		System.err.println( property );
	}
}
