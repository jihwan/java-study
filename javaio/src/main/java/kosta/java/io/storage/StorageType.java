package kosta.java.io.storage;

public enum StorageType {

	DelimiterTextStreamStorage ("delimiter-text-stream"),
	DelimiterTextStorage ("delimiter-text"),
	DelimiterDataStorage ("delimiter-data"),
	FixedDataStorage ("fixed-data"),
	FixedIndexingStorage ("fixed-indexing"),
	
	
	FixedIndexingDataStorage ("fixed-indexing-DDDDDDD"),
	;
    
    private String storageName;
    
    
    StorageType(String storageName) {
        this.storageName = storageName;
    }
    
    public String getStorageName() {
        return storageName;
    }
    
}
