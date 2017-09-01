package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

import java.util.Collections;

@Configuration
public class PointcutTransactionAspectConfig {
  @Bean
  PointcutTransactionAspect trxAspect() {
    PointcutTransactionAspect aspectOf = org.aspectj.lang.Aspects.aspectOf(PointcutTransactionAspect.class);
    aspectOf.setTransactionAttributeSource(transactionAttributeSource());
    return aspectOf;
  }

  // 원하는 transaction 처리 전략을 정의 한다.
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
}
