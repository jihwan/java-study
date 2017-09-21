package org.springframework.transaction.annotation;

import org.springframework.transaction.interceptor.TransactionAttributeSource;

/**
 * @author Jihwan Hwang
 */
public interface PointcutTransactionManagementConfigurer {

  TransactionAttributeSource transactionAttributeSource();
}
