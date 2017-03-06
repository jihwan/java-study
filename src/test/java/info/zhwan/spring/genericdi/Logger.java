package info.zhwan.spring.genericdi;

public interface Logger<T> {
	void log(T t);
}
