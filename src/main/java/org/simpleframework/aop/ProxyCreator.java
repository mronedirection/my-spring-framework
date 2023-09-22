package org.simpleframework.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * 使用Cglib创建动态代理对象
 * @author RenHao
 * @create 2023-08-17 17:12
 */
public class ProxyCreator {
    /**
     * 创建动态代理对象并返回
     * @param targetClass 被代理的Class对象
     * @param methodInterceptor 方法拦截器
     * @return
     */
    public static Object createProxy(Class<?> targetClass, MethodInterceptor methodInterceptor){
        return Enhancer.create(targetClass, methodInterceptor);
    }
}
