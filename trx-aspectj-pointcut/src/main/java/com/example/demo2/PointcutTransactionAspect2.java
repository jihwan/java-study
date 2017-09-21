package com.example.demo2;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.transaction.aspectj.AbstractTransactionAspect;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

/**
 * @see AnnotationTransactionAspect
 * @see AbstractTransactionAspect
 */
@Aspect
public class PointcutTransactionAspect2 extends AbstractTransactionAspect {

  protected PointcutTransactionAspect2() {
    super(null);
  }

  @Pointcut("execution(public * ((@org.springframework.stereotype.Service *)+).*(..)) && within(@org.springframework.stereotype.Service *)")
  private void trxPointcut() {}

  // @Override
  @Pointcut(value = "trxPointcut() && this(txObject)", argNames = "txObject")
  protected void transactionalMethodExecution(Object txObject) {
  }
}
