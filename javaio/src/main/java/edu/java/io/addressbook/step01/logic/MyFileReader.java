package edu.java.io.addressbook.step01.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import edu.java.io.addressbook.step01.domain.ZipAddress;
import edu.java.io.addressbook.util.FileUtil;
import edu.java.io.addressbook.util.Logger;

public class MyFileReader {
	//
	private static Logger logger = Logger.getLogger();

	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	private String pathName;
	private String fileName;

	public MyFileReader(MyFileConfig config) {
		//
		this.pathName = config.getPathName();
		this.fileName = config.getFileName();
	}

	public BufferedReader readFile() {
		//
		return readFile(DEFAULT_CHARSET);
	}

	private BufferedReader readFile(Charset charset) {
		//
		try {
			File file = FileUtil.readFile(pathName, fileName);
			FileInputStream fis = new FileInputStream(file);
			return new BufferedReader(new InputStreamReader(fis, charset));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public RandomAccessFile readRandomFile() {
		//
		return FileUtil.readRandomFile(pathName, fileName);
	}

	public long showFileContents(BufferedReader reader) {
		//
		// final int lsLength =
		// System.getProperty("line.separator").getBytes(DEFAULT_CHARSET).length;

		int count = 0;
		long offset = 0;
		String line = null;

		try {
			while ((line = reader.readLine()) != null) {
				count++;

				ZipAddress address = new ZipAddress(offset, line);
				logger.info(address);

				offset += line.getBytes(DEFAULT_CHARSET).length + 1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		logger.info("Address count --> " + count);
		return offset;
	}

	public long showRandomAccessFileContents(RandomAccessFile randomFile) {
		// linux 사용시를 고려하여, 
		 final int lsLength =
				 System.getProperty("line.separator").getBytes(DEFAULT_CHARSET).length;

		int count = 0;
		long offset = 0;
		String line = null;

		try {
			while ((line = randomFile.readLine()) != null) {
				count++;
				
				line = new String(line.getBytes(StandardCharsets.ISO_8859_1), DEFAULT_CHARSET);

				ZipAddress address = new ZipAddress(offset, line);
				logger.info(address);
				
				
				offset += randomFile.getFilePointer() + 1;
//				offset += line.getBytes(DEFAULT_CHARSET).length + 1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				randomFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		logger.info("Address count --> " + count);
		return offset;
	}
}