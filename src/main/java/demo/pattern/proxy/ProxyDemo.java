package demo.pattern.proxy;

import demo.pattern.proxy.cglib.AlipayMethodInterceptor;
import demo.pattern.proxy.cglib.CglibUtil;
import demo.pattern.proxy.impl.CommonPayment;
import demo.pattern.proxy.impl.ToCPaymentImpl;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyDemo {
    public static void main(String[] args) {
        //静态代理
//        ToCPayment toCProxy = new AlipayToC(new ToCPaymentImpl());
//        toCProxy.pay();
//        ToBPayment toBProxy = new AlipayToB(new ToBPaymentImpl());
//        toBProxy.pay();
        //使用JDK动态代理
        //创建目标类实例
//        ToCPayment toCPayment = new ToCPaymentImpl();
//        InvocationHandler handler = new AlipayInvocationHandler(toCPayment);
        //创建代理类
//        ToCPayment toCProxy = JdkDynamicProxyUtil.newProxyInstance(toCPayment,handler);
//        toCProxy.pay();
//        ToBPayment toBPayment = new ToBPaymentImpl();
//        InvocationHandler handlerToB = new AlipayInvocationHandler(toBPayment);
//        ToBPayment toBProxy = JdkDynamicProxyUtil.newProxyInstance(toBPayment, handlerToB);
//        toBProxy.pay();
        CommonPayment commonPayment = new CommonPayment();
        //使用JDK动态代理时，被代理类需要实现接口
//        AlipayInvocationHandler invocationHandler = new AlipayInvocationHandler(commonPayment);
//        CommonPayment commonPaymentProxy = JdkDynamicProxyUtil.newProxyInstance(commonPayment, invocationHandler);
        //使用Cglib动态代理
        MethodInterceptor methodInterceptor = new AlipayMethodInterceptor();
        //创建动态代理类
        CommonPayment commonPaymentProxy = CglibUtil.createProxy(commonPayment, methodInterceptor);
        commonPaymentProxy.pay();
        //创建被代理类实例
        ToCPayment toCPayment = new ToCPaymentImpl();
        ToCPayment toCProxy = CglibUtil.createProxy(toCPayment, methodInterceptor);
        toCProxy.pay();
    }
}
