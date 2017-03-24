package info.zhwan.spring.context;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.zhwan.beans.Bar;
import info.zhwan.beans.Foo;
import info.zhwan.beans.Zoo;
import info.zhwan.util.BeanDefinitionUtils;

import static org.junit.Assert.assertNotNull;

/**
 * <pre>
 * 이 테스트에서 흥미로운 점은,
 * {@link ParentConfig} class가 없이, {@link Bar}를 {@link MyConditionalTestConfig}에 bean 등록 한다면,
 * {@link Foo} 가 spring bean으로 등록이 안된다는 점이다.
 * 
 * 
 * @author zhwan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={
		MyConditionalTest.ParentConfig.class,
		MyConditionalTest.MyConditionalTestConfig.class
		})
public class MyConditionalTest {
	
	@Autowired
	GenericApplicationContext context;
	
	@Test
	public void testContext() throws Exception {
		assertNotNull(context);
		BeanDefinitionUtils.printBeanDefinitions(context);
	}
	
	@Configuration
	static class ParentConfig {
		@Bean
		Bar bar() {
			return new Bar();
		}
	}
	
	@Configuration
	static class MyConditionalTestConfig {
		@Bean
//		@MyConditional(Bar.class)
		@My2Conditional(clazz=Bar.class)
		Foo foo() {
			return new Foo();
		}
		
//		@Bean
//		Bar bar() {
//			return new Bar();
//		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Conditional(MyCondition.class)
	@interface MyConditional {
		Class<?> value() default Zoo.class;
	}
	
	/**
	 * spring4.2 부터 지원되는 meta annotaion attribute overriding 기법이다.
	 * annotation attribute가 unique 하다면 바로 overriding 가능 하겠지만,
	 * value 같은 경우는 어찌 할 바가 없었는데. spring4.2 부터 지원된다.
	 * 
	 * <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/new-in-4.2.html">
	 * new spring 4.2 features
	 * </a>
	 * 
	 * @author zhwan
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@MyConditional
	@interface My2Conditional {
		@AliasFor(annotation=MyConditional.class, attribute = "value")
		Class<?> clazz();
	}
	
	
	static class MyCondition implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			
			Class<?> c = (Class<?>)metadata.getAnnotationAttributes(MyConditional.class.getName()).get("value");
			try {
				BeanFactoryUtils.beanOfTypeIncludingAncestors(context.getBeanFactory(), c);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}
}
