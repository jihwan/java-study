package info.zhwan.orm.jpa.ch05;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author zhwan
 */
@Component
public class TestAdvisorBean extends AbstractBeanFactoryPointcutAdvisor implements InitializingBean {
  @Override
  public Pointcut getPointcut() {
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedName("initialize");
    return pointcut;
  }

  void aaa() {
    setAdvice(new MethodInterceptor() {
      @Override
      public Object invoke(MethodInvocation invocation) throws Throwable {

        try {
          System.err.println("BEGIN <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
          return invocation.proceed();
        } finally {
          System.err.println("END   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
      }
    });
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    aaa();
  }
}
