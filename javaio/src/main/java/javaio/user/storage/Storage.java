package javaio.user.storage;

import java.util.List;

public interface Storage {
	
	void save(List<String> data);
	void saveAll(List<List<String>> datas);
	
	List<String> find(String key);
}
