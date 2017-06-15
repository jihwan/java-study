package edu.java.io.addressbook.step02.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import edu.java.io.addressbook.step01.domain.ZipAddress;
import edu.java.io.addressbook.step02.config.MyFileConfig;
import edu.java.io.addressbook.util.Logger;

public class Indexer {
	//
	private static Logger logger = Logger.getLogger();
	private MyFileConfig config;

	public Indexer(MyFileConfig config) {
		//
		this.config = config;
	}

	public Map<String, List<Long>> buildDongMap(BufferedReader dataReader) {
		//
		Map<String, List<Long>> dongIndexMap = new HashMap<>();

		long offset = 0;
		String line = null;

		try {
			// discard first line
			line = dataReader.readLine();
			offset += line.getBytes(config.getCharset()).length + 1;

			while ((line = dataReader.readLine()) != null) {

				ZipAddress address = new ZipAddress(offset, line);
				String dong = address.getDong();

				if (dong == null || dong.equals("")) {
					logger.debug("dong is wrong --> " + address.toString());
					continue;
				}

				if (dongIndexMap.get(dong) == null) {
					dongIndexMap.put(dong, new ArrayList<Long>());
				}
				List<Long> offsetList = dongIndexMap.get(dong);
				offsetList.add(address.getOffset());

				offset += line.getBytes(config.getCharset()).length + 1;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dataReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("The size of DongIndexMap --> " + dongIndexMap.size());

		return dongIndexMap;
	}

	public void createDongIndexFile(BufferedWriter indexWriter, Map<String, List<Long>> indexMap) {
		// TODO:

		try {
			for (Iterator<String> iter = indexMap.keySet().iterator(); iter.hasNext();) {
				String key = iter.next();
				indexWriter.write(key);
				indexWriter.write("|");
				
				List<Long> list = indexMap.get(key);
				for (int i = 0; i < list.size(); i++) {
					indexWriter.write( String.valueOf(list.get(i)) );
					if (i != list.size() -1) {
						indexWriter.write(",");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				indexWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Long> findDongKeyOffset(BufferedReader indexReader, String dong) {
		//
		List<Long> dongOffsets = new ArrayList<>();
		String line = null;

		try {
			while ((line = indexReader.readLine()) != null) {

				StringTokenizer tokenizer = new StringTokenizer(line, "|");
				String dongName = tokenizer.nextToken();

				if (dongName.equals(dong)) {
					String offsetTokens = tokenizer.nextToken();
					tokenizer = new StringTokenizer(offsetTokens, ",");

					while (tokenizer.hasMoreTokens()) {
						String offset = tokenizer.nextToken();
						dongOffsets.add(Long.valueOf(offset));
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				indexReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dongOffsets;
	}

}