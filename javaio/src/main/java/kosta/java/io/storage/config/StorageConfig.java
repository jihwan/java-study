package kosta.java.io.storage.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

public enum StorageConfig {

    /** StorageConfig 싱글톤 객체 */
    INSTANCE
    ;

    private Properties properties;
    
    
    StorageConfig() {
        properties = new Properties();
        try {
            InputStream is = StorageConfig.class.getResourceAsStream("/storage-config.properties");
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public String basePath() {
        return getProperty("storage.basepath");
    }
    
    public Charset charset() {
        return Charset.forName(getProperty("storage.charset"));
    }
    
    public String extension() {
        return getProperty("storage.extension");
    }
}
