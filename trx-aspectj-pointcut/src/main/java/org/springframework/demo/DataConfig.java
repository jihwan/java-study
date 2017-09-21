package org.springframework.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.PointcutTransactionManagementConfigurer;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

import javax.sql.DataSource;
import java.util.Collections;

@Configuration
public class DataConfig implements PointcutTransactionManagementConfigurer {
  @Bean
  DataSource dataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    dataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
    dataSource.setDriverClass(org.h2.Driver.class);
    dataSource.setUsername("sa");
    return dataSource;
  }

  @Bean
  PlatformTransactionManager transactionManager() {
    DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource());
    dataSourceTransactionManager.setNestedTransactionAllowed(false);

    return dataSourceTransactionManager;
  }

  @Override
  public TransactionAttributeSource transactionAttributeSource() {
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