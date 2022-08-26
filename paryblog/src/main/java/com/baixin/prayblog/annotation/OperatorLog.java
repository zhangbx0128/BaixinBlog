package com.baixin.prayblog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangbaixin
 */
//注解用于方法
@Target(ElementType.METHOD)
//运行时使用
@Retention(RetentionPolicy.RUNTIME)
public @interface OperatorLog {
    String behavior() default "" ;
    String content() default "";
}
