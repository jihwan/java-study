package org.springframework.transaction.annotation;

import org.springframework.transaction.aspect.MyAspectJTransactionManagementConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @see TransactionManagementConfigurationSelector
 */
@Deprecated
public class MyTransactionManagementConfigurationSelector implements ImportSelector {

  @Override
  public String[] selectImports(AnnotationMetadata importingClassMetadata) {
    return new String[]{MyAspectJTransactionManagementConfiguration.class.getName()};
  }
}