package edu.java.io.addressbook.step02.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MyFileConfig {
	//
	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	
	private String pathName; 
	private String dataFileName;
	private String dongIndexFileName;
	
	private Charset charset;
	private String lineSeparator; 
	
	
	public MyFileConfig() {
		//
		this.pathName = "./src/main/resources/step02"; 
		this.dataFileName = "ZipCodeSeoul(UTF-8).dat"; 
		this.dongIndexFileName = "ZipCodeSeoulDong.idx";
		this.charset = DEFAULT_CHARSET;
		this.lineSeparator = System.getProperty("line.separator");
	}
	
	
	public String getPathName() {
		return pathName;
	}
	public String getDataFileName() {
		return dataFileName;
	}
	public Charset getCharset() {
	    return charset;
	}
	public String getDongIndexFileName() {
		return dongIndexFileName;
	}
	public String getLineSeparator() {
		return lineSeparator;
	}
}