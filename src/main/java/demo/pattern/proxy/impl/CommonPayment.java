package demo.pattern.proxy.impl;

/**
 * 使用Cglib动态代理目标类CommonPayment时，目标类不需要实现接口
 */
public class CommonPayment {
    public void pay() {
        System.out.println("个人名义或者公司名义都可以走这个支付通道");
    }
}
