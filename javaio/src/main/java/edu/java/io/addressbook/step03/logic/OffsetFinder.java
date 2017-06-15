package edu.java.io.addressbook.step03.logic;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import edu.java.io.addressbook.step01.domain.ZipAddress;
import edu.java.io.addressbook.step03.config.MyFileConfig;
import edu.java.io.addressbook.util.FileUtil;

public class OffsetFinder {
	// 
	private MyFileConfig config; 
	
	public OffsetFinder(MyFileConfig config) {
		//
		this.config = config; 
	}
	
	
	public List<ZipAddress> findAddressesWithOffsets(List<Long> offsets) {
		//
		RandomAccessFile dataFile = FileUtil.readRandomFile(config.getPathName(), config.getDataFileName());
		List<ZipAddress> zipAddresses = new ArrayList<>(offsets.size()); 
		
		try {
			String addressLine = null; 
			
			for (Long offset : offsets) { 
				dataFile.seek(offset); 
				addressLine = dataFile.readLine(); 
				addressLine = new String(addressLine.getBytes("ISO-8859-1"), "UTF-8"); 
				
				zipAddresses.add(new ZipAddress(offset, addressLine)); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dataFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return zipAddresses; 
	}
}
