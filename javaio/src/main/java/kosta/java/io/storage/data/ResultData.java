package kosta.java.io.storage.data;

import java.util.ArrayList;
import java.util.List;

public class ResultData {

    private List<String> data;
    
    
    public ResultData() {
        data = new ArrayList<>();
    }
    
    
    public void add(String value) {
        data.add(value);
    }
    
    public boolean hasData() {
        return data != null && data.size() > 0;
    }
    
    public List<String> getData() {
        return data;
    }
}
