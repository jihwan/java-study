package edu.java.io.addressbook.step03.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MyFileConfig {
	//
	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	
	private String pathName; 
	private String dataFileName;
	private String dongIndexFileName;
	private String dongIndexLocationFileName;
	
	private Charset charset;
	private String lineSeparator; 
	
	
	public MyFileConfig() {
		//
		this.pathName = "./src/main/resources/step03"; 
		this.dataFileName = "ZipCodeSeoul(UTF-8)-large.dat"; 
		this.dongIndexFileName = "ZipCodeSeoulDong.idx";
		this.dongIndexLocationFileName = "ZipCodeSeoulDong.idx.loc";
		
		this.charset = DEFAULT_CHARSET;
		this.lineSeparator = System.getProperty("line.separator");
	}
	
	
	public String getPathName() {
		return pathName;
	}
	public String getDataFileName() {
		return dataFileName;
	}
	public String getDongIndexFileName() {
		return dongIndexFileName;
	}
	public String getDongIndexLocationFileName() {
	    return dongIndexLocationFileName;
	}
	public Charset getCharset() {
        return charset;
    }
	public String getLineSeparator() {
		return lineSeparator;
	}
}