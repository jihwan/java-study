package org.springframework.transaction.aspect;

import org.springframework.transaction.aspectj.AbstractTransactionAspect;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;
import org.springframework.stereotype.Service;

/**
 * @see AnnotationTransactionAspect
 */
public aspect ServiceAnnotationTransactionAspect extends AbstractTransactionAspect {

  public ServiceAnnotationTransactionAspect() {
    // MyAspectJTransactionManagementConfiguration 에서 bean 생성시 setter inject 하기로 함!!!
    super(null);
  }

  /**
   * Matches the execution of any public method in a type with the Service
   * annotation, or any subtype of a type with the Service annotation.
   */
  private pointcut executionOfAnyPublicMethodInAtServiceType() :
      execution(public * ((@Service *)+).*(..)) && within(@Service *);

  protected pointcut transactionalMethodExecution(Object txObject):
      ( executionOfAnyPublicMethodInAtServiceType() ) && this(txObject);
}
