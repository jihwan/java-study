package edu.java.io.addressbook.step03.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import edu.java.io.addressbook.step01.domain.ZipAddress;
import edu.java.io.addressbook.step03.config.MyFileConfig;
import edu.java.io.addressbook.util.FileUtil;
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
		Map<String, List<Long>> dongIndexMap = new TreeMap<>();

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

	public void createDongIndexFile(BufferedWriter indexWriter, BufferedWriter indexLocationWriter,
			Map<String, List<Long>> indexMap) {
		// TODO:
		try {
			
			long offset = 0;
			
			for (Iterator<String> iter = indexMap.keySet().iterator(); iter.hasNext();) {
				
				indexLocationWriter.write(String.valueOf(offset));
				indexLocationWriter.newLine();
				
				String key = iter.next();
				List<Long> list = indexMap.get(key);
				
				StringBuilder line = new StringBuilder(list.size() * 10);
				
				
//				indexWriter.write(key);
//				indexWriter.write("|");
				line.append(key).append("|");

				for (int i = 0; i < list.size(); i++) {
//					indexWriter.write(String.valueOf(list.get(i)));
					line.append(String.valueOf(list.get(i)));
					if (i != list.size() - 1) {
//						indexWriter.write(",");
						line.append(",");
					}
				}
				
				indexWriter.write(line.toString());
				indexWriter.newLine();
				
				offset += line.toString().getBytes(config.getCharset()).length; 
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				indexWriter.close();
				indexLocationWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Long> getIndexOffsets(BufferedReader indexLocationReader) {
		//
		String line = null;
		List<Long> indexOffsets = new ArrayList<>();

		try {
			while ((line = indexLocationReader.readLine()) != null) {
				indexOffsets.add(Long.valueOf(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				indexLocationReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return indexOffsets;
	}

	public List<Long> findDongKeyOffset(List<Long> indexOffsets, BufferedReader indexReader, String dong) {
		//
		RandomAccessFile indexFile = FileUtil.readRandomFile(config.getPathName(), config.getDongIndexFileName());
		List<Long> dongOffsets = new LinkedList<>();

		String line = null;
		int startIndex = 0;
		int endIndex = indexOffsets.size();
		int mid;

		try {
			while (startIndex <= endIndex) {

				mid = (startIndex + endIndex) / 2;

				long indexLoc = indexOffsets.get(mid);
				indexFile.seek(indexLoc);
				line = indexFile.readLine();
				line = new String(line.getBytes("ISO-8859-1"), config.getCharset());

				StringTokenizer tokenizer = new StringTokenizer(line, "|");
				String dongName = tokenizer.nextToken();

				int comparison = dong.compareTo(dongName);

				if (comparison == 0) {
					String offsetTokens = tokenizer.nextToken();
					tokenizer = new StringTokenizer(offsetTokens, ",");

					while (tokenizer.hasMoreTokens()) {
						String offset = tokenizer.nextToken();
						dongOffsets.add(Long.valueOf(offset));
					}
					break;
				} else if (comparison < 0) {
					endIndex = mid - 1;
				} else {
					startIndex = mid + 1;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dongOffsets;
	}

}