package info.zhwan.spring.genericdi;

import org.springframework.stereotype.Component;

@Component
public class StringLogger implements Logger<String> {
	@Override
	public void log(String s) {
		System.out.println("strlog: " + s);
	}
}
