package info.zhwan.spring.aop;

import info.zhwan.beans.Foo;
import info.zhwan.beans.IFoo;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.ClassUtils;

/**
 * @author zhwan
 */
public class ProxyFactoryTest {
  @Test
  public void testSimple() throws Exception {
    ProxyFactory pf = new ProxyFactory();
    pf.setTarget(new Foo());
    pf.addAdvice(new MethodInterceptor() {
      @Override
      public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
          return invocation.proceed();
        } catch (Exception e) {
          throw e;
        }
      }
    });
    pf.addInterface(IFoo.class);
    Object proxy = pf.getProxy();
    System.err.println("proxy is " + proxy);

    Class<?>[] allInterfaces = ClassUtils.getAllInterfaces(proxy);
    for(Class<?> clazz : allInterfaces) {
      System.err.println("class is " + clazz);
    }






  }
}


