package info.zhwan.spring.orm;

import info.zhwan.beans.Bar;
import info.zhwan.beans.Foo;
import info.zhwan.beans.IFoo;
import info.zhwan.util.BeanDefinitionUtils;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationSource;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhwan
 */
public class HibernateSharedEntityManagerCreatorSimulationTest {
  @Test
  public void test() throws Exception {

    AnnotationConfigApplicationContext context =
      new AnnotationConfigApplicationContext(HibernateSharedEntityManagerCreatorSimulationTest.AppConfig.class);
    BeanDefinitionUtils.printBeanDefinitions(context);

    IFoo bean1 = context.getBean(IFoo.class);
    System.out.println(bean1);
    bean1.sayFoo();

    IFoo foo = context.getBean(TestBean.class).getFoo();
    System.out.println(foo);
    foo.sayFoo();
  }

  @Configuration
  @Import(Registrar.class)
  static class AppConfig {
    @Bean
    Bar bar() {
      return new Bar();
    }

    @Bean
    FooFactoryBean fooFactory() {
      return new FooFactoryBean();
    }

    @Bean
    TestBean testBean() {
      return new TestBean();
    }
  }

  static class TestBean implements InitializingBean, BeanFactoryAware {
    IFoo foo;

    public IFoo getFoo() {
      return foo;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
      this.foo = beanFactory.getBean(IFoo.class);
    }

    BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
      this.beanFactory = beanFactory;
    }
  }

  /**
   * <p>bean 초기화시 native EntityManagerFactory 를 생성해 둔다.</p>
   *
   * @see org.springframework.orm.jpa.AbstractEntityManagerFactoryBean
   */
  static class FooFactoryBean implements FactoryBean<FooFactory>, InitializingBean {

    private FooFactory fooFactory;

    @Override
    public FooFactory getObject() throws Exception {
      return this.fooFactory;
    }

    @Override
    public Class<?> getObjectType() {
      return FooFactory.class;
    }

    @Override
    public boolean isSingleton() {
      return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
      this.fooFactory = new FooFactoryImpl();
    }
  }

  /**
   * @see javax.persistence.EntityManagerFactory
   */
  interface FooFactory {
    Foo createFoo();
  }

  /**
   * @see org.hibernate.jpa.internal.EntityManagerFactoryImpl
   */
  static class FooFactoryImpl implements FooFactory {
    @Override
    public Foo createFoo() {
      return new Foo();
    }
  }

  /**
   * {@link org.springframework.orm.jpa.AbstractEntityManagerFactoryBean} BeanDefinition 정보를 이용하여,
   * {@link org.springframework.orm.jpa.SharedEntityManagerCreator} BeanDefinition을 만들어 bean registry에 등록.
   *
   * <p>원리는, {@link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean} 에서 {@link javax.persistence.EntityManagerFactory}
   * instance 가 초기화 되면, {@link org.springframework.orm.jpa.SharedEntityManagerCreator} bean 도 초기화가 이뤄진다.
   *
   * client 에서는 {@link javax.persistence.EntityManager} 를 필요로 하는데, {@link org.springframework.orm.jpa.SharedEntityManagerCreator} 에서
   * porxy된 {@link javax.persistence.EntityManager} 를 돌려준다.
   * </p>
   *
   * <p>
   *   {@link BeanFactoryPostProcessor} 를 이용하여, bean 설정정보를 변경할 수 있다.
   *   여기에서는, 어떤 bean 이 존재 할 경우, 그 bean 을 이용하여, 다른 bean 을 등록한다.
   * </p>
   *
   * @see org.springframework.data.jpa.repository.support.EntityManagerBeanDefinitionRegistrarPostProcessor
   */
  static class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
      String[] beanNames = beanFactory.getBeanNamesForType(FooFactoryBean.class);
      for (String beanName : beanNames) {
        beanName = transformedBeanName(beanName);

        BeanDefinitionBuilder builder = BeanDefinitionBuilder
          .rootBeanDefinition("info.zhwan.spring.orm.HibernateSharedEntityManagerCreatorSimulationTest.FooCreator");
        builder.setFactoryMethod("createFoo");
        builder.addConstructorArgReference(beanName);
        AbstractBeanDefinition bd = builder.getRawBeanDefinition();

        BeanDefinition definition = beanFactory.getBeanDefinition(beanName);
        bd.addQualifier(new AutowireCandidateQualifier(Qualifier.class, beanName));
        bd.setScope(definition.getScope());
        bd.setSource(definition.getSource());

        BeanDefinitionRegistry registry = BeanDefinitionRegistry.class.cast(beanFactory);
        registry.registerBeanDefinition("FooCreator#0", bd);
      }
    }
  }

  /**
   * @see org.springframework.orm.jpa.SharedEntityManagerCreator
   */
  static abstract class FooCreator {
    public static IFoo createFoo(final FooFactory fooFactory) {

      System.out.println(">>>>>>>>>>>>>>>>>>>> create Foo start");

      return (IFoo) Proxy.newProxyInstance(
        fooFactory.getClass().getClassLoader(),
        new Class<?>[]{IFoo.class},
        new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("equals")) {
              return (proxy == args[0]);
            } else if (method.getName().equals("hashCode")) {
              return hashCode();
            } else if (method.getName().equals("toString")) {
              return "Foo proxy for target factory [" + fooFactory + "]";
            }

            System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");
            Foo object = fooFactory.createFoo();
            return method.invoke(object, args);
          }
        }
      );
    }
  }

  /**
   * @see org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfigureRegistrar
   * @see org.springframework.data.repository.config.RepositoryConfigurationDelegate#registerRepositoriesIn(BeanDefinitionRegistry, RepositoryConfigurationExtension)
   * @see org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension#registerBeansForRoot(BeanDefinitionRegistry, RepositoryConfigurationSource)
   */
  static class Registrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
      RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(TestBeanFactoryPostProcessor.class);
      registry.registerBeanDefinition("TestBeanFactoryPostProcessor", rootBeanDefinition);
    }
  }

  private static String transformedBeanName(String name) {
    Assert.notNull(name, "'name' must not be null");
    String beanName = name;
    while (beanName.startsWith(BeanFactory.FACTORY_BEAN_PREFIX)) {
      beanName = beanName.substring(BeanFactory.FACTORY_BEAN_PREFIX.length());
    }
    return beanName;
  }
}

