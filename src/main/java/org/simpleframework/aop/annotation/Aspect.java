package org.simpleframework.aop.annotation;

import java.lang.annotation.*;

/**
 * 标记切面类
 * @author RenHao
 * @create 2023-08-17 16:05
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
//    Class<? extends Annotation> value();
    String pointcut();
}
