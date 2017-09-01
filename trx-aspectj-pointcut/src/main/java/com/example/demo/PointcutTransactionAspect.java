package com.example.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.transaction.aspectj.AbstractTransactionAspect;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StopWatch;

/**
 * @see AnnotationTransactionAspect
 * @see AbstractTransactionAspect
 */
@Aspect
public class PointcutTransactionAspect extends TransactionAspectSupport implements DisposableBean {

  // 원하는 pointcut expression 을 정의 한다
  @Pointcut("execution(* com.example..*Service.*(..))")
  void trxPointcut() {
  }

  @Around("trxPointcut()")
  public Object writerLogAround(ProceedingJoinPoint joinPoint) throws Throwable {

//		재정의 코드
    MethodSignature methodSignature = MethodSignature.class.cast(joinPoint.getSignature());
    Object txObject = joinPoint.getTarget();
    StopWatch stopWatch = new StopWatch(txObject.getClass().getSimpleName() + "." + methodSignature.getName() + "(" + joinPoint.getArgs() + ")");

    // Adapt to TransactionAspectSupport's invokeWithinTransaction...
    try {
      stopWatch.start();
      return invokeWithinTransaction(methodSignature.getMethod(), txObject.getClass(), new InvocationCallback() {
        public Object proceedWithInvocation() throws Throwable {
          return joinPoint.proceed(); //proceed(txObject); // 재정의 코드
        }
      });
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Error err) {
      throw err;
    } catch (Throwable thr) {
      Rethrower.rethrow(thr);
      throw new IllegalStateException("Should never get here", thr);
    }
//		재정의 코드
    finally {
      stopWatch.stop();
      System.out.println(">>> " + stopWatch);
    }
  }

  // 코드를 copy & paste 하였다.
  private static class Rethrower {

    public static void rethrow(final Throwable exception) {
      class CheckedExceptionRethrower<T extends Throwable> {
        @SuppressWarnings("unchecked")
        private void rethrow(Throwable exception) throws T {
          throw (T) exception;
        }
      }
      new CheckedExceptionRethrower<RuntimeException>().rethrow(exception);
    }
  }

  // 코드를 copy & paste 하였다.
  @Override
  public void destroy() {
    clearTransactionManagerCache(); // An aspect is basically a singleton
  }
}
