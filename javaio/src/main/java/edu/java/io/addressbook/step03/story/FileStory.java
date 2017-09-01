package edu.java.io.addressbook.step03.story;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.java.io.addressbook.step01.domain.ZipAddress;
import edu.java.io.addressbook.step03.config.MyFileConfig;
import edu.java.io.addressbook.step03.logic.Indexer;
import edu.java.io.addressbook.step03.logic.MyFileReaderWriter;
import edu.java.io.addressbook.step03.logic.OffsetFinder;
import edu.java.io.addressbook.util.Logger;

public class FileStory {
	//
	private static Logger logger = Logger.getLogger();
	
	private MyFileConfig config; 
	private MyFileReaderWriter fileReader;
	private Indexer indexer; 
	private OffsetFinder offsetFinder; 
	
	
	public FileStory() {
		//
		this.config = new MyFileConfig();
		this.fileReader = new MyFileReaderWriter(config);
		this.indexer = new Indexer(config);
		this.offsetFinder = new OffsetFinder(config); 
	}
	
	
	public static void main(String[] args) {
		// 
		FileStory story = new FileStory(); 
		
//		story.buildIndex(); 
		// raf/raf : 21263ms, 21041ms, 22417ms
		// br/raf  : 2529ms, 2451ms, 2409ms
		// br/br   : 348ms, 334ms, 318ms
		// large : 4761ms, 3876ms, 3796ms
		
		story.findUsingIndex("내발산동");
		// raf : 1187ms, 1191ms, 1163ms
		// br  : 228ms, 252ms, 223ms
		// binary search : 452ms, 445ms, 436,
		
		// large bs :
	}
	
	public void buildIndex() {
		// 
		logger.info("Create index file...");
		logger.startDeferred();
		
		BufferedReader reader = fileReader.readDataFile();
		Map<String,List<Long>> indexMap = indexer.buildDongMap(reader);

		
		
		
//		for (String key: indexMap.keySet() ) {
//			System.out.println( key + ":" + indexMap.get(key));
//		}
		
		BufferedWriter indexWriter = fileReader.writeDongIndexFile();
		BufferedWriter indexLocationWriter = fileReader.writeDongIndexLocationFile();
		indexer.createDongIndexFile(indexWriter, indexLocationWriter, indexMap);
		
		logger.logDeferred(true);
		logger.displayTime("buildIndex");
	}
	
	public void findUsingIndex(String dong) {
		// 
		logger.info(String.format("Read %s offset values from index file...", dong));
		
		
		BufferedReader indexReader = fileReader.readDongIndexFile();
		BufferedReader indexLocationReader = fileReader.readDongIndexLocationFile();
		
		List<Long> indexOffsets = indexer.getIndexOffsets(indexLocationReader);
		logger.startDeferred();
		List<Long> dongOffsets = indexer.findDongKeyOffset(indexOffsets, indexReader, dong);
		
		logger.info(String.format("Offset count %d, %s", dongOffsets.size(), dongOffsets.toString()));  
		
		
		logger.info("\n\tFinding zip address..."); 
		List<ZipAddress> addresses = offsetFinder.findAddressesWithOffsets(dongOffsets);
		
		
		// Display
		int index = 0; 
		
		for(ZipAddress address : addresses) {
			logger.info(String.format("[%04d] %s", index++, address));
		}
		logger.logDeferred(true);
        logger.displayTime("findUsingIndex");
	}
}