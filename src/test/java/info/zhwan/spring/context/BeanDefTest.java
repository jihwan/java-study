package info.zhwan.spring.context;

import info.zhwan.beans.Bar;
import info.zhwan.beans.Foo;
import info.zhwan.beans.IFoo;
import info.zhwan.util.BeanDefinitionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AutowireCandidateQualifier;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Query;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhwan
 */
public class BeanDefTest {
  @Test
  public void test() throws Exception {

    AnnotationConfigApplicationContext context =
      new AnnotationConfigApplicationContext(BeanDefTest.AppConfig.class);

    BeanDefinitionBuilder builder = BeanDefinitionBuilder
      .rootBeanDefinition("info.zhwan.spring.context.BeanDefTest.FooCreator");
    builder.setFactoryMethod("createFoo");
    builder.addConstructorArgReference("foo");
    AbstractBeanDefinition bd = builder.getRawBeanDefinition();


    context.registerBeanDefinition("foo#0", bd);


    BeanDefinitionUtils.printBeanDefinitions(context);


    IFoo ifoo = context.getBean("foo#0", IFoo.class);
    System.err.println("IFoo is " + ifoo);
    ifoo.sayFoo();


    FooProxy bean = context.getBean(FooProxy.class);
    bean.sayFoo();
  }

  public static abstract class FooCreator {
    public static FooProxy createFoo(final Foo foo) {
      return (FooProxy) Proxy.newProxyInstance(
        Thread.currentThread().getContextClassLoader(),
        new Class<?>[]{ FooProxy.class },
        new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("equals")) {
              return (proxy == args[0]);
            }
            else if (method.getName().equals("hashCode")) {
              return hashCode();
            }
            else if (method.getName().equals("toString")) {
              return "foo...";
            }

            return method.invoke(foo, args);
          }
        }
      );
    }
  }

  @Configuration
  public static class AppConfig {
    @Bean
    Bar bar() {
      return new Bar();
    }

    @Bean
    Foo foo() {
      return new Foo();
    }
  }

  public static interface FooProxy extends IFoo {
  }

}

