package info.zhwan.spring.genericdi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

	@Autowired Logger<String> logger;
	
	@Override
	public void hello(String name) {
		logger.log("hi " + name);
	}
}
