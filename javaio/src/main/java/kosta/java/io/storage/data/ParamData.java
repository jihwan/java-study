package kosta.java.io.storage.data;

import java.util.ArrayList;
import java.util.List;

public class ParamData {

    private List<ColumnData> data;
    
    public ParamData() {
        data = new ArrayList<>();
    }
    
    public void add(String data, ColumnSchema schema) {
        this.data.add(new ColumnData(data, schema));
    }
    
    public void addString(String value) {
        data.add(new ColumnData(value));
    }
    
    public List<ColumnData> getData() {
        return data;
    }
    
    public List<String> getDataAsString() {
        
        List<String> stringData = new ArrayList<>();
        
        for (ColumnData data: this.data) {
            stringData.add(data.getData());
        }
        return stringData;
    }
}
