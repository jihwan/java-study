package org.springframework.transaction.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.transaction.aspect.MyAspectJTransactionManagementConfiguration;

import java.lang.annotation.*;

/**
 * @see EnableTransactionManagement
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MyAspectJTransactionManagementConfiguration.class)
public @interface EnableAspectJPointcutTransactionManagement {
}
