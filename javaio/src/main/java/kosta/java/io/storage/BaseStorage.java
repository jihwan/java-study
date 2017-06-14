package kosta.java.io.storage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import kosta.java.io.storage.config.StorageConfig;
import kosta.java.io.storage.data.ParamData;

public abstract class BaseStorage implements Storage {
    
    protected static final String LS = System.getProperty("line.separator");  // \n\r , \n
    
    protected String storageName;
    protected File storageFile;
    
    
    protected BaseStorage(String storageName) {
        this.storageName = storageName;
        this.storageFile = getStorageFile();
    }
    
    protected File getStorageFile() {
        //
        File storageDir = new File(StorageConfig.INSTANCE.basePath());
        
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File file = new File(StorageConfig.INSTANCE.basePath(), storageName + StorageConfig.INSTANCE.extension());
        
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    
    protected File getStorageTempFile() {
        //
        File storageDir = new File(StorageConfig.INSTANCE.basePath());
        
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File file = new File(StorageConfig.INSTANCE.basePath(), storageName + StorageConfig.INSTANCE.extension() + "_temp");
        
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    
    protected static byte[] toBytes(String text) throws UnsupportedEncodingException {
        return text.getBytes(StorageConfig.INSTANCE.charset());
    }
    
    protected static String getKey(ParamData data) {
        return data.getData().get(0).getData();
    }
}
