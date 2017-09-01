package com.springtest.trx.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.*;

/**
 * @see EnableTransactionManagement
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MyTransactionManagementConfigurationSelector.class)
public @interface EnableTrxPointcutDrivenMgr {
}
