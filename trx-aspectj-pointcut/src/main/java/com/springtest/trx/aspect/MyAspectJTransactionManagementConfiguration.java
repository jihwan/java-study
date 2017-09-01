package com.springtest.trx.aspect;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.AbstractTransactionManagementConfiguration;
import org.springframework.transaction.aspectj.AspectJTransactionManagementConfiguration;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

import java.util.Collections;

/**
 * @see AspectJTransactionManagementConfiguration
 */
@Configuration
public class MyAspectJTransactionManagementConfiguration extends AbstractTransactionManagementConfiguration {

  final PlatformTransactionManager txManager;

  // PlatformTransactionManager 을 DI 받는다.
  MyAspectJTransactionManagementConfiguration(PlatformTransactionManager txManager) {
    this.txManager = txManager;
  }

  @Bean(name = "a.b.c.d.e.f.g")//(name = TransactionManagementConfigUtils.TRANSACTION_ASPECT_BEAN_NAME)
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  public PointcutTransactionAspect myTransactionAspect() {
    PointcutTransactionAspect txAspect = PointcutTransactionAspect.aspectOf();
    if (this.txManager != null) {
      txAspect.setTransactionManager(this.txManager);
    }
    // 내가 재정의한 코드
    super.txManager = this.txManager;
    // transaction aspect 에서 필요로 하는 TransactionAttributeSource를 정의 및 setup
    txAspect.setTransactionAttributeSource(transactionAttributeSource());

    return txAspect;
  }


  TransactionAttributeSource transactionAttributeSource() {
    RuleBasedTransactionAttribute get = new RuleBasedTransactionAttribute();
    get.setReadOnly(true);
    get.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);

    RuleBasedTransactionAttribute select = new RuleBasedTransactionAttribute();
    select.setReadOnly(true);
    select.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    RuleBasedTransactionAttribute etc = new RuleBasedTransactionAttribute();
    etc.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    etc.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Throwable.class)));

    NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
    source.addTransactionalMethod("get*", get);
    source.addTransactionalMethod("select*", select);
    source.addTransactionalMethod("insert*", etc);
    return source;
  }

  // 구현없는 오버라이딩. 사용하지도 않고 쓸데 없는 exception 발생하기 때문
  @Override
  public void setImportMetadata(AnnotationMetadata importMetadata) {
  }
}
