package kosta.java.io.storage.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import kosta.java.io.storage.BaseStorage;
import kosta.java.io.storage.config.StorageConfig;
import kosta.java.io.storage.data.ColumnData;
import kosta.java.io.storage.data.ParamData;
import kosta.java.io.storage.data.ResultData;

public class FixedDataStorage extends BaseStorage {

	private static final String DE = ",";
	private static final String DELETE_FLAG = "_DELETED";

	
	public FixedDataStorage(String storageName) {
	    super(storageName);
	}
	
	
	@Override
	public void insert(ParamData data) {

		try (
	        FileOutputStream fos = new FileOutputStream(this.storageFile, true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, StorageConfig.INSTANCE.charset()));
            
		){
		    DataOutputStream dos = new DataOutputStream(fos);
			insert(bw, dos, data);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertMany(List<ParamData> datas) {
	    
		try (
		    FileOutputStream fos = new FileOutputStream(this.storageFile, true);
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, StorageConfig.INSTANCE.charset()));
		){
		    DataOutputStream dos = new DataOutputStream(fos);
		    
			for (ParamData data : datas) {
				insert(bw, dos, data);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void insert(Writer writer, DataOutputStream os, ParamData data) throws IOException {

		for (ColumnData columnData: data.getData()) {
		
	        writer.write(columnData.getData());
	        
	        int length = columnData.getData().getBytes(StorageConfig.INSTANCE.charset()).length;
	        String format = "%" + (columnData.getSchema().getSize() - length) + "s"; 
	        
	        writer.write(String.format(format, ""));
		    writer.write(DE);
		}
		writer.write(LS);
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
                    result.add(column.trim());
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
        
        if (columns[columnIndex].trim().equals(value)) {
            
            ResultData result = new ResultData();
            
            // Data 객체 생성, 리턴
            for (String column: columns) {
                result.add(column.trim());
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
                    
                    for (ColumnData columnData : data.getData()) {
                        writer.write(columnData.getData());
                        
                        int length = columnData.getData().getBytes(StorageConfig.INSTANCE.charset()).length;
                        String format = "%" + (columnData.getSchema().getSize() - length) + "s"; 
                        
                        writer.write(String.format(format, ""));
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
