package edu.java.io.addressbook.step02.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

import edu.java.io.addressbook.step02.config.MyFileConfig;
import edu.java.io.addressbook.util.FileUtil;

public class MyFileReaderWriter {
	//
	private MyFileConfig config;

	public MyFileReaderWriter(MyFileConfig config) {
		//
		this.config = config;
	}

	
	public BufferedReader readFile() {
		//
		try {
			File file = FileUtil.readFile(config.getPathName(), config.getDataFileName());
			FileInputStream fis = new FileInputStream(file);
			return new BufferedReader(new InputStreamReader(fis, config.getCharset()));
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public BufferedWriter writeDongIndexFile() {
	    //
        try {
            File file = FileUtil.readFile(config.getPathName(), config.getDongIndexFileName());
            FileOutputStream fos = new FileOutputStream(file);
            return new BufferedWriter(new OutputStreamWriter(fos, config.getCharset()));
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
	}
	
	public BufferedReader readDongIndexFile() {
	    //
	    try {
            File file = FileUtil.readFile(config.getPathName(), config.getDongIndexFileName());
            FileInputStream fis = new FileInputStream(file);
            return new BufferedReader(new InputStreamReader(fis, config.getCharset()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
	}

	public RandomAccessFile readRandomFile() {
		//
		return FileUtil.readRandomFile(config.getPathName(), config.getDataFileName());
	}

}