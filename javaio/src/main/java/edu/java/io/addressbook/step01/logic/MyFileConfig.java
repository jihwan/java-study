package edu.java.io.addressbook.step01.logic;

public class MyFileConfig {
	//
	private String pathName; 
	private String fileName; 
	
	public MyFileConfig() {
		//
		this.pathName = "./src/main/resources/step01"; 
		this.fileName = "ZipCodeSeoul(UTF-8).dat"; 
	}
	
	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder(); 
		
		builder.append("pathName:").append(pathName); 
		builder.append(", fileName:").append(fileName); 
		
		return builder.toString(); 
	}

	public String getPathName() {
		return pathName;
	}
	public String getFileName() {
		return fileName;
	}
	
}