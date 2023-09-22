package demo.pattern.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * 创建动态代理类
 */
public class CglibUtil {
    public static <T>T createProxy(T targetObject, MethodInterceptor methodInterceptor){
        return (T)Enhancer.create(targetObject.getClass(), methodInterceptor);
    }
}
