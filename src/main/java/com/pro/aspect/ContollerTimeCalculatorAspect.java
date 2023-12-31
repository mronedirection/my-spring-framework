package com.pro.aspect;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.DefaultAspect;

import java.lang.reflect.Method;

/**
 * @author RenHao
 * @create 2023-08-17 20:32
 */
@Slf4j
@Aspect(pointcut = "execution(* com.pro.controller.frontend..*.*(..))")
//@Aspect(value = Controller.class) //将切面类植入Controller业务类中
@Order(0)
public class ContollerTimeCalculatorAspect extends DefaultAspect {
    // 记录时间
    private long timestampCache;

    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        log.info("开始计时，执行的类是[{}]，执行的方法是[{}]，参数是[{}]",
                targetClass.getName(), method.getName(), args);
        timestampCache = System.currentTimeMillis();
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        long endTime = System.currentTimeMillis();
        long costTime = endTime - timestampCache;
        log.info("结束计时，执行的类是[{}]，执行的方法是[{}]，参数是[{}]，返回值是[{}]，耗费时间为[{}]ms",
                targetClass.getName(), method.getName(), args, returnValue, costTime);
        return returnValue;
    }
}
