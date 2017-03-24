package info.zhwan.spring.context;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import info.zhwan.util.BeanDefinitionUtils;

/**
 * <pre>
 * 외부에서 주입한 조건에 따라 특정 bean 생성을 제어 하고자 할 때 사용함.
 * 
 * 이 테스트는 system property에 runenv 설정하였고, 값으로 dev or prod 라는 값을 지정토록 하여,
 * 이 값에 의해 빈 생성 전략이 반영되는 테스트 코드를 작성하였다.
 * 
 * @author zhwan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ConditionalTest.ConditionalTestConfig.class})
public class ConditionalTest {
	
	private static final String RUNENV = "runenv";
	private static final String DEV = "dev";
	private static final String PROD = "prod";
	
	@Autowired
	GenericApplicationContext context;
	
	@BeforeClass
	public static void beforeClasses() {
		System.setProperty(RUNENV, PROD);
	}
	
	@Test
	public void testContext() throws Exception {
		Assert.notNull(context);
		BeanDefinitionUtils.printBeanDefinitions(context);
		
		DummyDataSource dummyDataSource = context.getBean(DummyDataSource.class);
		dummyDataSource.foo();
	}
	
	@Configuration
	static class ConditionalTestConfig {
		@Bean
		@Conditional(ProdCondition.class)
		DummyDataSource hikariCpDataSource() {
			return new HikariCpDataSource();
		}
		
		@Bean
		@Conditional(DevCondition.class)
		DummyDataSource boneCpDataSource() {
			return new BoneCpDataSource();
		}
	}
	
	interface DummyDataSource {
		void foo();
	}
	
	static class HikariCpDataSource implements DummyDataSource {
		@Override
		public void foo() {
			System.out.println(this.getClass().getName());
		}
	}
	
	static class BoneCpDataSource implements DummyDataSource {
		@Override
		public void foo() {
			System.out.println(this.getClass().getName());
		}
	}
	
	static class DevCondition implements Condition {

		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			String runenv = context.getEnvironment().getProperty(RUNENV, String.class, "");
			Assert.hasText(runenv);
			return runenv.equals(DEV);
		}
	}
	
	static class ProdCondition implements Condition {

		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			String runenv = context.getEnvironment().getProperty(RUNENV, String.class, "");
			Assert.hasText(runenv);
			return runenv.equals(PROD);
		}
	}
}
