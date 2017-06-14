package kosta.java.io.storage.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import kosta.java.io.storage.config.StorageConfig;

public class Indexer {

	private String storageName;

	public Indexer(String storageName) {
		this.storageName = storageName;
	}

	public void addIndex(String key, long location) {
		//
		File indexFile = getIndexFile();

		try (BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(indexFile, true), StorageConfig.INSTANCE.charset()));) {
			bw.write(key);
			bw.write(",");
			bw.write(String.valueOf(location));
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Long getLocation(String key) {

		File indexFile = getIndexFile();

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(indexFile), StorageConfig.INSTANCE.charset()));) {
			String line = null;

			while ((line = reader.readLine()) != null) {
				String[] columns = line.split(",");

				if (columns[0].equals(key)) {
					return Long.valueOf(columns[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	

	private File getIndexFile() {
		//
		File storageDir = new File(StorageConfig.INSTANCE.basePath());

		if (!storageDir.exists()) {
			storageDir.mkdirs();
		}
		File file = new File(StorageConfig.INSTANCE.basePath(), this.storageName + ".idx");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
}
