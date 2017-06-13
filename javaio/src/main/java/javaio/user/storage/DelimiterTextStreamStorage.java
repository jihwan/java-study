package javaio.user.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javaio.user.domain.User;

/**
 * 데이터와 데이터간 구분은 line separator 로 한다.
 * @author kosta
 *
 */
public class DelimiterTextStreamStorage implements Storage {

	final String DEFAULT_FILEPATH = "user-storage.data";
	
	final String DEFAULT_DELEMITER = ",";
	
	final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	String filePath = DEFAULT_FILEPATH;
	
	String delemiter = DEFAULT_DELEMITER;
	
	File file;
	
	public DelimiterTextStreamStorage() {
		file = new File(filePath);
	}
	
	@Override
	public void save(List<String> data) {
		try( FileOutputStream fos = new FileOutputStream(file, true);) {
			for (String value : data) {
				fos.write(value.getBytes());
				fos.write(delemiter.getBytes());
			}
			fos.write(LINE_SEPARATOR.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void saveAll(List<List<String>> datas) {
		for (List<String> data : datas) {
			this.save(data);
		}
	}

//	@Override
//	public List<String> find(String key) {
//		try( BufferedReader br = new BufferedReader( new FileReader(file) );  ) {
//
//			String line;
//			while ((line = br.readLine()) != null) {
//				String[] row = line.split("[,]");
//				if ( key.equals( row[0] ) ) {
//					return Arrays.asList(row);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	@Override
	public List<String> find(String key) {
		try( FileInputStream fis = new FileInputStream(file);) {
			
			int read;
			int oldRead = 100000;
			
			String d = "";
			while ( (read = fis.read()) != -1 ) {
				if ( oldRead == 44 && read == 13) {
					// 한 줄 읽음
					String[] row = d.split("[,]");
					d = "";
					
					if ( key.equals( row[0].trim() ) ) {
						return Arrays.asList(row);
					}
				}
				else {
					// string 을 합친다.
					String s = String.valueOf((char)read);
					d += s;
				}
				
				
				oldRead = read;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
