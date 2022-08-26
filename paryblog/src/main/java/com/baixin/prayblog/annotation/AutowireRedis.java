package com.baixin.prayblog.annotation;

import java.lang.annotation.*;

/**
 * @author zhangdada
 * @Since 2022-8-9
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutowireRedis {
}