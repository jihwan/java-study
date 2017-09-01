package com.springtest.trx.aspect;

import org.springframework.transaction.aspectj.AbstractTransactionAspect;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

/**
 * @see AnnotationTransactionAspect
 */
public aspect PointcutTransactionAspect extends AbstractTransactionAspect {

  public PointcutTransactionAspect() {
    // MyAspectJTransactionManagementConfiguration 에서 bean 생성시 setter inject 하기로 함!!!
    super(null);
  }

  // 원하는 pointcut expression 을 작성하자
  private pointcut all():
      execution(* com.springtest..*Service.*(..));

  protected pointcut transactionalMethodExecution(Object txObject):
      ( all() ) && this(txObject);
}
