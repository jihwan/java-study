package org.springframework.transaction.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AbstractTransactionManagementConfiguration;
import org.springframework.transaction.annotation.PointcutTransactionManagementConfigurer;
import org.springframework.transaction.aspectj.AspectJTransactionManagementConfiguration;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.util.Assert;

/**
 * @see AspectJTransactionManagementConfiguration
 */
@Configuration
public class MyAspectJTransactionManagementConfiguration extends AbstractTransactionManagementConfiguration {

  private final PlatformTransactionManager txManager;

  private TransactionAttributeSource transactionAttributeSource;

  // PlatformTransactionManager 을 DI 받는다.
  MyAspectJTransactionManagementConfiguration(PlatformTransactionManager txManager) {
    this.txManager = txManager;
  }

  @Bean(name = "a.b.c.d.e.f.g")//(name = TransactionManagementConfigUtils.TRANSACTION_ASPECT_BEAN_NAME)
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  public ServiceAnnotationTransactionAspect myTransactionAspect() {
    ServiceAnnotationTransactionAspect txAspect = ServiceAnnotationTransactionAspect.aspectOf();
    if (this.txManager != null) {
      txAspect.setTransactionManager(this.txManager);
    }
    // 내가 재정의한 코드
    super.txManager = this.txManager;

    // transaction aspect 에서 필요로 하는 TransactionAttributeSource를 정의 및 setup
    txAspect.setTransactionAttributeSource(transactionAttributeSource);

    return txAspect;
  }

  @Autowired//(required = false)
  void setConfigurers(PointcutTransactionManagementConfigurer configurer) {
    Assert.notNull(configurer, "PointcutTransactionManagementConfigurer must not be null");
    this.transactionAttributeSource = configurer.transactionAttributeSource();
    Assert.notNull(this.transactionAttributeSource, "transactionAttributeSource must not be null");
  }

  // 구현없는 오버라이딩. 사용하지도 않고 쓸데 없는 exception 발생하기 때문
  @Override
  public void setImportMetadata(AnnotationMetadata importMetadata) {
  }
}
