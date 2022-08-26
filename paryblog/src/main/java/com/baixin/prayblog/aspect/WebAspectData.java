package com.baixin.prayblog.aspect;


import com.baixin.prayblog.annotation.DataChange;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangdada
 * @Since 2022-8-9
 */
@Slf4j
@Aspect
@Component
public class WebAspectData {

    @Autowired(required = false)
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 切入点
     *切入点,基于注解实现的切入点  加上该注解的都是Aop切面的切入点
     *
     */
    @Pointcut("@annotation(com.baixin.prayblog.annotation.DataChange)")
    public void pointCut(){

    }
    /**
     * 环绕通知
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     * @param proceedingJoinPoint
     */
    @Around("pointCut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        ScheduledExecutorService task = new ScheduledThreadPoolExecutor(10, new BasicThreadFactory.
                Builder().namingPattern("clearCache-schedule-pool-%d").build());

        log.info("----------- 环绕通知 -----------");
        log.info("环绕通知的目标方法名：" + proceedingJoinPoint.getSignature().getName());
        //获取方法上的注解
        Signature signature1 = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature1;
        Method targetMethod = methodSignature.getMethod();
        //方法对象
        DataChange annotation = targetMethod.getAnnotation(DataChange.class);
        //反射得到自定义注解的方法对象
        String name = annotation.name();
        //获取自定义注解的方法对象的参数即name
        Set<String> keys = stringRedisTemplate.keys("*" + name + "*");
        log.info("3");
        //模糊定义key
        Long delete = stringRedisTemplate.delete(keys);
        log.info("4");
        //模糊删除redis的key值
        //执行加入双删注解的改动数据库的业务 即controller中的方法业务
        Object proceed = null;
        try {
            proceed = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


        task.schedule(() -> {
            log.info("5");
            Set<String> keys1 = stringRedisTemplate.keys("*" + name + "*");//模糊删除
//                    Long delete = stringRedisTemplate.delete(keys);
            stringRedisTemplate.delete(keys1);
        }, 2L, TimeUnit.SECONDS);

        return proceed;
        //返回业务代码的值

    }



}
