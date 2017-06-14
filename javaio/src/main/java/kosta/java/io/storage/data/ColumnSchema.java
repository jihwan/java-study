package kosta.java.io.storage.data;

public class ColumnSchema {

    private String name;
    private int size;   // optional, only typs is string
    
    public ColumnSchema(String name) {
        this.name = name;
    }
    
    public ColumnSchema(String name, int size) {
        this.name = name;
        this.size = size;
    }
    
    @Override
    public String toString() {
        return "ColumnSchema [name=" + name + ", size=" + size + "]";
    }

    public String getName() {
        return name;
    }
    public int getSize() {
        return size;
    }
    
    
}
