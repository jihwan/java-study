package kosta.java.io.storage;

import java.util.List;

import kosta.java.io.storage.data.ParamData;
import kosta.java.io.storage.data.ResultData;

public interface Storage {

	void insert(ParamData data);
	void insertMany(List<ParamData> datas);
	
	ResultData select(String key);
	List<ResultData> selectAll();
	List<ResultData> selectBy(int columnIndex, String value);
	
	void update(String key, ParamData data);
	void delete(String key);
}
