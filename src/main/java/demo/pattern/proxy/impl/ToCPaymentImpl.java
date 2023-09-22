package demo.pattern.proxy.impl;

import demo.pattern.proxy.ToCPayment;

/**
 * 目标类：具体实现类
 */
public class ToCPaymentImpl implements ToCPayment {
    @Override
    public void pay() {
        System.out.println("以用户的名义进行支付");
    }
}
