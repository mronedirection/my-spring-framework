package org.simpleframework.aop;

import com.pro.controller.superadmin.HeadLineOperationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.inject.DependencyInjector;

public class AspectWeaverSpringAOPTest {

    //需要先进行AOP操作，植入切面类，生成动态代理对象，替换容器中的原始类
    //再进行IOC操作，否则注入的对象是原始类对象，而不是代理类对象，出现错误
    @DisplayName("织入通用逻辑测试：doAop")
    @Test
    public void doAopTest(){
        // 初始化容器
        BeanContainer beanContainer = BeanContainer.getInstance();
        // 加载Bean
        beanContainer.loadBeans("com.pro");
        // Aop织入
//        new AspectWeaverSpringAOP().doAop();
        // 依赖注入
        new DependencyInjector().doIoc();

        HeadLineOperationController controller =
                (HeadLineOperationController) beanContainer.getBean(HeadLineOperationController.class);

//        controller.addHeadLine(null, null);
    }
}
