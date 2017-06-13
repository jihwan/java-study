package javaio.user.storage;

import java.util.HashMap;
import java.util.Map;

public class StorageFactory {
	
	Map<StorageType, Storage> map = new HashMap<>();
	
	public StorageFactory() {
		map.put(StorageType.DelimiterTextStreamStorage, new DelimiterTextStreamStorage());
	}

	public Storage getStorage( StorageType storageType ) {
		if (map.containsKey(storageType) == false) {
			throw new IllegalArgumentException(storageType + " is not supported");
		}
		
		return map.get(storageType);
	}
}
