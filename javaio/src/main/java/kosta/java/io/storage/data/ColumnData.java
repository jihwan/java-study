package kosta.java.io.storage.data;

public class ColumnData {

    private String data;
    private ColumnSchema schema;

    
    public ColumnData(String data, ColumnSchema schema) {
        this.data = data;
        this.schema = schema;
    }
    public ColumnData(String data) {
        this.data = data;
    }

    
    public String getData() {
        return (String) data;
    }
    public ColumnSchema getSchema() {
        return schema;
    }
    
}
