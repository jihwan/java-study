package info.zhwan.spring.context;

import info.zhwan.util.BeanDefinitionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * <pre>
 * {@link ConditionalOnClass}의 기능은,
 * {@link ConditionalOnClass} attribute에 등록된 class 정보가,
 * classpath에 존재 해야 만 bean 등록 가능 하다.
 *
 * @author zhwan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConditionalOnClassTest.ConditionalOnClassTestConfig.class})
public class ConditionalOnClassTest {

  @Autowired
  GenericApplicationContext context;

  @Test(expected = NoSuchBeanDefinitionException.class)
  public void testContext() throws Exception {
    assertNotNull(context);
    BeanDefinitionUtils.printBeanDefinitions(context);

    context.getBean(ConditionalOnClassTestConfig.class);
  }

  /**
   * 테스트를 위해서 말도 안되는 class 정보를 주었다.
   *
   * @author zhwan
   */
  @Configuration
  @ConditionalOnClass(name = {
    "x.x.Foo"
  })
  static class ConditionalOnClassTestConfig {
  }
}
