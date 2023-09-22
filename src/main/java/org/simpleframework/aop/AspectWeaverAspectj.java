package org.simpleframework.aop;

import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.aop.aspect.DefaultAspect;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 引入 AspectJ的切面表达式和相关的定位解析机制，让 pointcut 更加灵活
 * @author RenHao
 * @create 2023-08-17 17:46
 */
public class AspectWeaverAspectj {
    private BeanContainer beanContainer;

    public AspectWeaverAspectj() {
        this.beanContainer = BeanContainer.getInstance();
    }

    public void doAop(){
        //1.获取所有的切面类
        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
        if (ValidationUtil.isEmpty(aspectSet)) {
            return;
        }
        // 2.拼装AspectInfoList
        List<AspectInfo> aspectInfoList = packAspectInfoList(aspectSet);
        // 3.遍历容器里的类
        Set<Class<?>> classSet = beanContainer.getClasses();
        for (Class<?> targetClass : classSet) {
            // 排除bean里的AspectClass:不能给Aspect类植入切面逻辑，否则陷入死循环
            if (targetClass.isAnnotationPresent(Aspect.class)) {
                continue;
            }
            // 4.初筛符合条件的Aspect
            List<AspectInfo> roughMatchedAspectList = collectRoughMatchedAspectListForSpecificClass(aspectInfoList, targetClass);
            if (ValidationUtil.isEmpty(roughMatchedAspectList)) {
                continue;
            }
            // 5.尝试进行Aspect的织入
            wrapIfNecessary(roughMatchedAspectList, targetClass);
        }
    }

    /**
     * 根据获取到的所有的切面类拼装AspectInfoList
     *
     * @param aspectSet
     * @return
     */
    private List<AspectInfo> packAspectInfoList(Set<Class<?>> aspectSet) {
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        for (Class<?> aspectClass : aspectSet) {
            if (verifyAspect(aspectClass)) {
                // 1.获取aspectClass中的order和Aspect标签中的属性
                Order orderTag = aspectClass.getAnnotation(Order.class);
                Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
                // 2.创建PointcutLocator
                PointcutLocator pointcutLocator = new PointcutLocator(aspectTag.pointcut());
                // 3.从容器中获取切面类的实例
                DefaultAspect aspect = (DefaultAspect) beanContainer.getBean(aspectClass);
                // 4.创建AspectInfo封装类
                AspectInfo aspectInfo = new AspectInfo(orderTag.value(), aspect, pointcutLocator);
                aspectInfoList.add(aspectInfo);
            } else {
                throw new RuntimeException("@Aspect and @Order have not been added to the Aspect class, " +
                        "or Aspect class does not extend from DefaultAspect");
            }
        }
        return aspectInfoList;
    }

    /**
     * 初筛符合条件的Aspect
     * @param aspectInfoList
     * @param targetClass
     * @return
     */
    private List<AspectInfo> collectRoughMatchedAspectListForSpecificClass(List<AspectInfo> aspectInfoList, Class<?> targetClass) {
        List<AspectInfo> roughMatchedAspectList = new ArrayList<>();
        for (AspectInfo aspectInfo : aspectInfoList) {
            if (aspectInfo.getPointcutLocator().roughMatches(targetClass)) {
                roughMatchedAspectList.add(aspectInfo);
            }
        }
        return roughMatchedAspectList;
    }

    /**
     * 尝试进行Aspect的织入
     * 生成动态代理对象替换容器中的原始被代理目标对象
     * @param roughMatchedAspectList
     * @param targetClass
     */
    private void wrapIfNecessary(List<AspectInfo> roughMatchedAspectList, Class<?> targetClass) {
        //Cglib创建动态代理对象需要的方法拦截器
        AspectListExecutor executor = new AspectListExecutor(targetClass, roughMatchedAspectList);
        // 创建动态代理对象
        Object proxyBean = ProxyCreator.createProxy(targetClass, executor);
        // 将动态代理对象添加到容器中，取代被代理类实例
        beanContainer.addBean(targetClass, proxyBean);
    }


    /**
     * 用来验证Aspect类是否满足自定义切面类的要求
     * 首先一定要遵守给Aspect类添加@Aspect标签和@Order标签的规范
     * 同时，一定要继承自DefaultAspect.class
     * 此外，@Aspect的属性不能是它本身
     *
     * @param aspectClass
     * @return
     */
    private boolean verifyAspect(Class<?> aspectClass) {
        return aspectClass.isAnnotationPresent(Aspect.class) &&
                aspectClass.isAnnotationPresent(Order.class) &&
                DefaultAspect.class.isAssignableFrom(aspectClass);
    }
}
