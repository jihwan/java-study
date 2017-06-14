package kosta.java.io.storage.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import kosta.java.io.storage.BaseStorage;
import kosta.java.io.storage.config.StorageConfig;
import kosta.java.io.storage.data.ParamData;
import kosta.java.io.storage.data.ResultData;

public class DelimiterTextStorage extends BaseStorage {

	private static final String DE = ",";
	private static final String DELETE_FLAG = "_DELETED";
	
	public DelimiterTextStorage(String storageName) {
	    super(storageName);
	}
	
	
	@Override
	public void insert(ParamData data) {

		try (
			FileOutputStream fos = new FileOutputStream(this.storageFile, true);
		){
			insert(fos, data);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertMany(List<ParamData> datas) {
	    
		try (
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.storageFile, true), StorageConfig.INSTANCE.charset()));
		){
			for (ParamData data : datas) {
				insert(new FileOutputStream(this.storageFile, true), data);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void insert(OutputStream fos, ParamData data) throws IOException {
		for (String value: data.getDataAsString()) {
			fos.write(value.getBytes());
			fos.write(DE.getBytes());
		}
		fos.write(LS.getBytes());
	}

	
	@Override
    public ResultData select(String key) {

        ResultData result = new ResultData();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(this.storageFile))) {
            
            String lineBuffer = null;
            
            while ( (lineBuffer = reader.readLine()) != null) {
                ResultData resultData = convertResult(lineBuffer, 0, key);
                
                if (resultData != null) {
                    result = resultData;
                    break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
	
	@Override
    public List<ResultData> selectAll() {
	    
	    List<ResultData> results = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(this.storageFile))) {
            
            String lineBuffer = null;
            
            while ( (lineBuffer = reader.readLine()) != null) {
                String[] columns = lineBuffer.split(DE);
                ResultData result = new ResultData();
                
                if (columns[0] == null || columns[0].equals(DELETE_FLAG)) {
                    continue;
                }
                
                // Data 객체 생성, 리턴
                for (String column: columns) {
                    result.add(column);
                }
                results.add(result);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }


    @Override
    public List<ResultData> selectBy(int columnIndex, String value) {
        
        List<ResultData> results = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(this.storageFile))) {
            
            String lineBuffer = null;
            
            while ( (lineBuffer = reader.readLine()) != null) {
                ResultData resultData = convertResult(lineBuffer, columnIndex, value);
                
                if (resultData != null) {
                    results.add(resultData);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
    
    private ResultData convertResult(String lineBuffer, int columnIndex, String value) {
        
        String[] columns = lineBuffer.split(DE);
        
        if (columns[columnIndex].equals(value)) {
            
            ResultData result = new ResultData();
            
            // Data 객체 생성, 리턴
            for (String column: columns) {
                result.add(column);
            }
            return result;
        }
        return null;
    }
    

    @Override
    public void update(String key, ParamData data) {
        //
        File tempFile = getStorageTempFile();
        
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.storageFile), StorageConfig.INSTANCE.charset()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), StorageConfig.INSTANCE.charset()));
        ) {
            String line = null;
            
            while ( (line = reader.readLine()) !=null ) {
                
                ResultData result = convertResult(line, 0, key);
                
                if (result != null) {
                    
                    for (String columnValue : data.getDataAsString()) {
                        writer.write(columnValue);
                        writer.write(DE);
                    }
                }
                else {
                    writer.write(line);
                }
                writer.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        this.storageFile.delete();
        tempFile.renameTo(this.storageFile);
        this.storageFile = tempFile;
    }

    @Override
    public void delete(String key) {

        File tempFile = getStorageTempFile();
        
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.storageFile), StorageConfig.INSTANCE.charset()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), StorageConfig.INSTANCE.charset()));
        ) {
            String line = null;
            
            while ( (line = reader.readLine()) !=null ) {
                
                ResultData result = convertResult(line, 0, key);
                
                if (result != null) {
                    writer.write(DELETE_FLAG);
                }
                else {
                    writer.write(line);
                }
                writer.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        this.storageFile.delete();
        tempFile.renameTo(this.storageFile);
        this.storageFile = tempFile;
    }

}
