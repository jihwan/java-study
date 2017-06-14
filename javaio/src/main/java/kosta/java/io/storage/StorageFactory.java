package kosta.java.io.storage;

import kosta.java.io.storage.impl.DelimiterTextStorage;
import kosta.java.io.storage.impl.DelimiterTextStreamStorage;
import kosta.java.io.storage.impl.FixedDataStorage;
import kosta.java.io.storage.impl.FixedIndexingDataStorage;

public class StorageFactory {

	public static Storage getStorage(StorageType type, String storeName) {
        
	    String storageName = getStorageName(type, storeName);
	    
        switch (type) {
        case DelimiterTextStreamStorage:
            return new DelimiterTextStreamStorage(storageName);
        case DelimiterTextStorage:
            return new DelimiterTextStorage(storageName);
        case FixedDataStorage:
            return new FixedDataStorage(storageName);
        case FixedIndexingDataStorage:
        		return new FixedIndexingDataStorage(storageName);
        default:
            throw new RuntimeException("StorageType이 유요하지 않습니다. -> " + type);
        }
    }
	
	private static String getStorageName(StorageType type, String storeName) {
	    return storeName + "-" + type.getStorageName();
	}
}
