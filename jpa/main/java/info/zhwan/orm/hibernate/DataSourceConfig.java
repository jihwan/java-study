//package info.zhwan.orm.hibernate;
//
//import com.vladmihalcea.flexypool.FlexyPoolDataSource;
//import com.vladmihalcea.flexypool.adaptor.HikariCPPoolAdapter;
//import com.vladmihalcea.flexypool.config.Configuration;
//import com.vladmihalcea.flexypool.connection.ConnectionDecoratorFactoryResolver;
//import com.vladmihalcea.flexypool.metric.MetricsFactoryResolver;
//import com.vladmihalcea.flexypool.strategy.IncrementPoolOnTimeoutConnectionAcquiringStrategy;
//import com.vladmihalcea.flexypool.strategy.RetryConnectionAcquiringStrategy;
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.context.annotation.Bean;
//
//import javax.sql.DataSource;
//import java.util.concurrent.TimeUnit;
//
//@org.springframework.context.annotation.Configuration
//public class DataSourceConfig {
//
//  final String uniqueId = "abcdefg-";
//
//  @Bean//(initMethod = "start", destroyMethod = "close")
//  HikariDataSource hikariDataSource() {
//    HikariConfig config = new HikariConfig();
//    config.setDriverClassName(org.h2.Driver.class.getName());
//    config.setJdbcUrl("jdbc:h2:tcp://localhost/~/testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=PostgreSQL");
//    config.setUsername("sa");
//    config.setPassword("");
//    config.setMinimumIdle(10);
//    config.setMaximumPoolSize(10);
//    config.setIdleTimeout(600000);
//    config.setMaxLifetime(3600000);
//    config.setConnectionTimeout(30000);
//    config.setValidationTimeout(5000);
//    config.setLeakDetectionThreshold(0); // 0 is disable
//    config.setPoolName("HicariCP");
//    return new HikariDataSource(config);
//  }
//
//  @Bean
//  Configuration<HikariDataSource> configuration(HikariDataSource hikariDataSource) {
//    return new Configuration.Builder<HikariDataSource>(
//      uniqueId,
//      hikariDataSource,
//      (configurationProperties) -> new HikariCPPoolAdapter(configurationProperties)
////      BitronixPoolAdapter.FACTORY
//    )
//      .setMetricsFactory(MetricsFactoryResolver.INSTANCE.resolve())
//      .setConnectionProxyFactory(ConnectionDecoratorFactoryResolver.INSTANCE.resolve())
//      .setMetricLogReporterMillis(TimeUnit.SECONDS.toMillis(5))
//      .setJmxEnabled(true)
//      .setJmxAutoStart(true)
//      .setConnectionAcquireTimeThresholdMillis(50L)
//      .setConnectionLeaseTimeThresholdMillis(250L)
////      .setEventListenerResolver(new EventListenerResolver() {
////        @Override
////        public List<EventListener<? extends Event>> resolveListeners() {
////          return Arrays.<EventListener<? extends Event>>asList(
////            new ConnectionAcquireTimeoutEventListener(),
////            new ConnectionAcquireTimeThresholdExceededEventListener(),
////            new ConnectionLeaseTimeThresholdExceededEventListener()
////          );
////        }
////      })
//      .build();
//  }
//
//
//  @Bean(initMethod = "start", destroyMethod = "stop")
//  public FlexyPoolDataSource dataSource(Configuration<HikariDataSource> configuration) {
////    Configuration<HikariDataSource> configuration = configuration();
//    return new FlexyPoolDataSource<HikariDataSource>(configuration,
//      new IncrementPoolOnTimeoutConnectionAcquiringStrategy.Factory(5),
//      new RetryConnectionAcquiringStrategy.Factory(2)
//    );
//  }
//}
