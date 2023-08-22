package org.simpleframework.aop.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simpleframework.aop.PointcutLocator;

/**
 * 用来封装Aspect的执行顺序和实现类等信息
 * @author RenHao
 * @create 2023-08-17 16:49
 */
@AllArgsConstructor
@Getter
public class AspectInfo {
    // order优先级信息
    private int orderIndex;

    // Aspect实现类
    private DefaultAspect aspectObject;

    // 解析Aspect表达式并且定位被织入的目标
    private PointcutLocator pointcutLocator; //引入Aspectj才需要此变量
}
