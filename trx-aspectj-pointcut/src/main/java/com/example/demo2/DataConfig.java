package com.example.demo2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataConfig {
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
}