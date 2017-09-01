package edu.java.io.addressbook.step01.story;

import java.io.BufferedReader;
import java.io.RandomAccessFile;

import edu.java.io.addressbook.step01.logic.MyFileConfig;
import edu.java.io.addressbook.step01.logic.MyFileReader;
import edu.java.io.addressbook.util.Logger;

public class FileStory {
	//
	private MyFileReader fileReader; 
	
	public FileStory() {
		//
		this.fileReader = new MyFileReader(new MyFileConfig()); 
	}
	
	public static void main(String[] args) {
		// 
		FileStory story = new FileStory(); 
		
		story.showFileContents();
//		story.showRandomAccessFileContents();
	}
	
	public void showFileContents() {
		// 
	    Logger logger = Logger.getLogger();
	    logger.startDeferred();
	    
	    BufferedReader reader = fileReader.readFile();
	    long contentsSize = fileReader.showFileContents(reader);
	    
	    long executionTime = logger.logDeferred(true);
	    displayTime("showFileContents", executionTime, contentsSize);
	}
	
	public void showRandomAccessFileContents() {
		//
	    Logger logger = Logger.getLogger();
        logger.startDeferred();
	    
		RandomAccessFile randomFile = fileReader.readRandomFile(); 
		long contentsSize = fileReader.showRandomAccessFileContents(randomFile);
		
		long executionTime = logger.logDeferred(true);
		displayTime("showRandomAccessFileContents", executionTime, contentsSize);
	}
	
	private void displayTime(String name, long executionTime, long contentsSize) {
	    
	    long perSecond = contentsSize / executionTime * 1000;
	    
	    String format = "%-30s - time: %dms, contentsSize: %d bytes, %d KB/s";
	    System.out.println(String.format(format, name, executionTime, contentsSize, perSecond / 1024));
	}
}