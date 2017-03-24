package info.zhwan.spring.genericdi;

import org.springframework.stereotype.Component;

@Component
public class ObjectLogger implements Logger<Object> {
	@Override
	public void log(Object s) {
		System.out.println("objlog: " + s);
	}
}
