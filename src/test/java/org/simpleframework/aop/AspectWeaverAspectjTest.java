package org.simpleframework.aop;

import com.pro.controller.superadmin.HeadLineOperationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.inject.DependencyInjector;

/**
 * @author RenHao
 * @create 2023-08-18 11:47
 */
public class AspectWeaverAspectjTest {
    @DisplayName("织入通用逻辑测试：doAop")
    @Test
    public void doAopTest(){
        // 初始化容器
        BeanContainer beanContainer = BeanContainer.getInstance();
        // 加载Bean
        beanContainer.loadBeans("com.pro");
        // Aop织入
        new AspectWeaverAspectj().doAop();
        // 依赖注入
        new DependencyInjector().doIoc();

        HeadLineOperationController controller =
                (HeadLineOperationController) beanContainer.getBean(HeadLineOperationController.class);

//        controller.addHeadLine(null, null);
    }
}
