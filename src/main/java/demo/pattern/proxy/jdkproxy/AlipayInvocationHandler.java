package demo.pattern.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK动态代理
 */
public class AlipayInvocationHandler implements InvocationHandler {
    //目标类：被代理的对象
    private Object targetObject;
    public AlipayInvocationHandler(Object targetObject){
        this.targetObject = targetObject;
    }

    /**
     *
     * @param proxy 被代理的目标对象
     *
     * @param method 被代理的方法实例
     *
     * @param args 被代理的方法入参
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforePay();
        Object result = method.invoke(targetObject, args);
        afterPay();
        return result;
    }

    private void beforePay() {
        System.out.println("从招行取款");
    }
    private void afterPay() {
        System.out.println("支付给慕课网");
    }
}
