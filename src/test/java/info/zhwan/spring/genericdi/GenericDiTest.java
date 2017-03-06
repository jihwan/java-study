package info.zhwan.spring.genericdi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.zhwan.util.BeanDefinitionUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppContext.class})
public class GenericDiTest {

	@Autowired GenericApplicationContext context;
	@Autowired HelloService helloService;
	
	@Test
	public void testGeniricDi() throws Exception {
		BeanDefinitionUtils.printBeanDefinitions(context);
		helloService.hello("zhwan");
	}
}
