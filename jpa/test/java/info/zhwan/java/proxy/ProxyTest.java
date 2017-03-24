package info.zhwan.java.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

  @Test
  public void test1() {
    Class<?>[] interfaces = new Class<?>[] { Hello.class };
    Hello hello = (Hello)Proxy.newProxyInstance(
      ProxyTest.class.getClassLoader(),
      interfaces,
      new HelloInvocationHandler(new HelloImpl(), false)
    );

    String spring = hello.hi("spring");
    System.err.println(spring);
  }

  @Test
  public void test2() {
    Class<?>[] interfaces = new Class<?>[] { HelloProxy.class };
    HelloProxy helloProxy = (HelloProxy)Proxy.newProxyInstance(
      ProxyTest.class.getClassLoader(),
      interfaces,
      new HelloInvocationHandler(new HelloImpl(), false)
    );

    String spring = helloProxy.hi("spring");
    System.err.println(spring);
    System.err.println( helloProxy.isTransactional() );
  }

}

class HelloInvocationHandler implements InvocationHandler {

  private volatile Hello target;

  private boolean transactional = false;

  public HelloInvocationHandler(Hello hello, boolean transactional) {
    this.target = hello;
    this.transactional = transactional;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    String methodName = method.getName();

    if (methodName.equals("isTransactional")) {
      return this.transactional;
    }

    return method.invoke(this.target, args);
  }
}

class HelloImpl implements Hello {
  @Override
  public String hi(String name) {
    return name + " hihi";
  }
}