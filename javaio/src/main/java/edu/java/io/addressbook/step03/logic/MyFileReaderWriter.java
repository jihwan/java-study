package edu.java.io.addressbook.step03.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

import edu.java.io.addressbook.step03.config.MyFileConfig;
import edu.java.io.addressbook.util.FileUtil;

public class MyFileReaderWriter {
	//
	private MyFileConfig config;

	public MyFileReaderWriter(MyFileConfig config) {
		//
		this.config = config;
	}

	
	public BufferedReader readDataFile() {
		//
	    return readFile(config.getPathName(), config.getDataFileName());
	}
	
	public BufferedReader readDongIndexFile() {
        //
	    return readFile(config.getPathName(), config.getDongIndexFileName());
    }
	
	public BufferedReader readDongIndexLocationFile() {
	    return readFile(config.getPathName(), config.getDongIndexLocationFileName());
	}
	
	private BufferedReader readFile(String pathName, String fileName) {
	    //
        try {
            File file = FileUtil.readFile(pathName, fileName);
            FileInputStream fis = new FileInputStream(file);
            return new BufferedReader(new InputStreamReader(fis, config.getCharset()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
	}
	
	public BufferedWriter writeDongIndexFile() {
	    //
        return writeFile(config.getPathName(), config.getDongIndexFileName());
	}
	
	public BufferedWriter writeDongIndexLocationFile() {
	    //
	    return writeFile(config.getPathName(), config.getDongIndexLocationFileName());
	}
	
	private BufferedWriter writeFile(String pathName, String fileName) {
	    try {
            File file = FileUtil.readFile(pathName, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            return new BufferedWriter(new OutputStreamWriter(fos, config.getCharset()));
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
	}
	
	public RandomAccessFile readRandomFile() {
		//
		return FileUtil.readRandomFile(config.getPathName(), config.getDataFileName());
	}

}