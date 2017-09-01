package com.springtest.trx.annotation;

import com.springtest.trx.aspect.MyAspectJTransactionManagementConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.transaction.annotation.TransactionManagementConfigurationSelector;

/**
 * @see TransactionManagementConfigurationSelector
 */
public class MyTransactionManagementConfigurationSelector implements ImportSelector {// extends AdviceModeImportSelector<EnableTrxPointcutDrivenMgr> {

  @Override
  public String[] selectImports(AnnotationMetadata importingClassMetadata) {
    return new String[]{MyAspectJTransactionManagementConfiguration.class.getName()};
  }
}