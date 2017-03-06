package info.zhwan.orm.jpa.ch05;

import info.zhwan.util.BeanDefinitionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Ch05Main {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch05Main.class)) {

      System.err.println("=================================================================================");
      BeanDefinitionUtils.printBeanDefinitions(context);


      Tester tester = context.getBean(Tester.class);
      System.err.println("=================================================================================");
      tester.initialize();

      System.err.println("=================================================================================");
      tester.search();
//      System.err.println("=================================================================================");
//      tester.searchJqpl();



//      System.err.println("=================================================================================");
//      tester.biDirection();

//      System.err.println("=================================================================================");
//      tester.update();

//      System.err.println("=================================================================================");
//      tester.delete();


    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @Component
  class TestComponent implements BeanPostProcessor, Ordered, InitializingBean {


    @Override
    public int getOrder() {
      return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
      System.err.println("aaaaaaaaaaaaaa");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      return bean;
    }
  }
}
