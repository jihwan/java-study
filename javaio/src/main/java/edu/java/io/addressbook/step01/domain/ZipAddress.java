package edu.java.io.addressbook.step01.domain;

import java.util.StringTokenizer;

public class ZipAddress {
	//
	private long offset; 
	private String zipCode; 
	private String city; 
	private String gungu; 
	private String roadName; 
	private String buildingName; 
	private String dong; 
	
	public ZipAddress(String line) {
		//
		this(0L, line); 
	}

	public ZipAddress(long offset, String line) {
		//
		this.offset = offset;
		
		if (line == null || line.isEmpty()) {
			initWithNull();
		} else {
			StringTokenizer tokenizer = new StringTokenizer(line, "\t"); 
			
			this.zipCode = tokenizer.nextToken();  
			this.city = tokenizer.nextToken(); 
			this.gungu = tokenizer.nextToken(); 
			this.roadName = tokenizer.nextToken(); 
			if (tokenizer.countTokens() == 2) {
				this.buildingName = tokenizer.nextToken();
			}
			this.dong = tokenizer.nextToken(); 
		}
	}
	
	private void initWithNull() {
		// 
		this.zipCode = "N/A"; 
		this.city = "N/A"; 
		this.gungu = "N/A"; 
		this.roadName = "N/A"; 
		this.buildingName = "N/A"; 
		this.dong = "N/A"; 
	}

	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder(); 
		
		builder.append("offset:").append(offset); 
		builder.append(", zipCode:").append(zipCode); 
		builder.append(", city:").append(city); 
		builder.append(", gungu:").append(gungu); 
		builder.append(", roadName:").append(roadName); 
		builder.append(", buildingName:").append(buildingName); 
		builder.append(", dong:").append(dong); 
		
		return builder.toString(); 
	}
	
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGungu() {
		return gungu;
	}

	public void setGungu(String gungu) {
		this.gungu = gungu;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}
	
	public long getOffset() {
		return offset; 
	}
	
	public void setOffset(long offset) {
		this.offset = offset; 
	}
}