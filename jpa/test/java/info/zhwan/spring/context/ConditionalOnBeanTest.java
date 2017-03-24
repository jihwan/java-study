package info.zhwan.spring.context;

import info.zhwan.beans.Bar;
import info.zhwan.beans.Foo;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import info.zhwan.util.BeanDefinitionUtils;

import static org.junit.Assert.assertNotNull;
import static org.springframework.util.Assert.isInstanceOf;

/**
 * <pre>
 * {@link ConditionalOnBean} 의 기능은, 
 * {@link ConditionalOnBean} attribute에 정의한 bean 정보가,
 * spring container에 spring bean으로 등록되어 있을 경우에만, bean 등록이 가능하다.
 *  
 * @author zhwan
 *
 */
public class ConditionalOnBeanTest {

	@Test(expected=NoSuchBeanDefinitionException.class)
	public void testExceptionApplicationContext() {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(ExceptionTestConfig.class);
		assertNotNull(context);
		context.getBean(ExceptionTestConfig.class);
	}
	
	@Test
	public void testApplicationContext() {
		
		// 클래스 부모 자식간 등록 순서 영향이 있다!!!
		GenericApplicationContext context = 
				new AnnotationConfigApplicationContext(AppParentConfig.class, AppConfig.class);
		assertNotNull(context);
		
		isInstanceOf(AppConfig.class, context.getBean(AppConfig.class));
		
		BeanDefinitionUtils.printBeanDefinitions(context);
	}
	
	@Test(expected=NoSuchBeanDefinitionException.class)
	public void testExceptionBean() {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig2.class);
		assertNotNull(context);
		
		context.getBean(Bar.class);
	}
	
	@Test
	public void testBean() {
		
		GenericApplicationContext context = new AnnotationConfigApplicationContext(AppConfig2.class);
		assertNotNull(context);
		
		isInstanceOf(AppConfig2.class, context.getBean(AppConfig2.class));
		
		BeanDefinitionUtils.printBeanDefinitions(context);
	}
	
	@Configuration
	@ConditionalOnBean(Foo.class)
	static class ExceptionTestConfig {
	}
	
	@Configuration
	static class AppParentConfig {
		@Bean
		Foo foo() {
			return new Foo();
		}
	}
	
	@Configuration
	@ConditionalOnBean(Foo.class)
	static class AppConfig {
	}
	
	@Configuration
	static class AppConfig2 {
		@Bean
		@ConditionalOnBean(Foo.class)
		Bar bar() {
			return new Bar();
		}
	}
}
