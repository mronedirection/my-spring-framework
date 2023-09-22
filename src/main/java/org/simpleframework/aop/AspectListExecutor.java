package org.simpleframework.aop;

import lombok.Getter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.util.ValidationUtil;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Cglib动态代理
 * 创建 MethodInterceptor 的实现类 AspectListExecutor
 * 实现 Aspect 横切逻辑以及被代理对象方法的定序执行
 * @author RenHao
 * @create 2023-08-17 16:20
 */
public class AspectListExecutor implements MethodInterceptor {
    //被代理的类
    private Class<?> targetClass;
    //排好序的Aspect列表，切面实现类的封装信息，包含切面对象和执行顺序信息
    @Getter
    private List<AspectInfo> sortedAspectInfoList;

    public AspectListExecutor(Class<?> targetClass, List<AspectInfo> aspectInfoList){
        this.targetClass = targetClass;
        // 对aspectInfoList中的横切对象进行排序处理
        this.sortedAspectInfoList = sortAspectInfoList(aspectInfoList);
    }
    /**
     * 按照order的值进行升序排序，确保order值小的aspect先被织入
     *
     * @param aspectInfoList
     * @return
     */
    private List<AspectInfo> sortAspectInfoList(List<AspectInfo> aspectInfoList) {
        Collections.sort(aspectInfoList, new Comparator<AspectInfo>() {
            @Override
            public int compare(AspectInfo o1, AspectInfo o2) {
                //按照值的大小进行升序排序
                return o1.getOrderIndex() - o2.getOrderIndex();
            }
        });
        return aspectInfoList;
    }

    /**
     * 代理逻辑
     *
     * @param proxy       动态代理对象的引用
     * @param method      被代理的目标方法
     * @param args        被代理的目标方法对应的参数列表
     * @param methodProxy 被代理方法的代理方法对象实例
     * @throws Throwable
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object returnValue = null;
        // AspectJ的精筛
        collectAccurateMatchedAspectList(method); //使用springaop不需要此行
        if(ValidationUtil.isEmpty(sortedAspectInfoList)){
            // 如果精筛后没有需要执行的切面，就执行被代理类的原方法即可
            returnValue = methodProxy.invokeSuper(proxy, args);
            return returnValue;
        }
        //1.按照order的顺序升序执行完所有Aspect的before方法
        invokeBeforeAdvices(method, args);
        try{
            //2.执行被代理类的方法
            returnValue = methodProxy.invokeSuper(proxy, args);
            //3.如果被代理方法正常返回，则按照order的顺序降序执行完所有Aspect的afterReturning方法
            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
        } catch (Exception e){
            //4.如果被代理方法抛出异常，则按照order的顺序降序执行完所有Aspect的afterThrowing方法
            invokeAfterThrowingAdvides(method, args, e);
        }
        return returnValue;
    }

    /**
     * 利用AspectJ的精筛
     * @param method
     */
    private void collectAccurateMatchedAspectList(Method method) {
        if (ValidationUtil.isEmpty(sortedAspectInfoList)) {
            return;
        }
        // 由于需要边遍历边删除，所以这里用迭代器
        Iterator<AspectInfo> iterator = sortedAspectInfoList.iterator();
        while (iterator.hasNext()){
            AspectInfo aspectInfo = iterator.next();
            if (!aspectInfo.getPointcutLocator().accurateMatches(method)) {
                // 精准匹配失败就移除
                iterator.remove();
            }
        }

    }


    //4.如果被代理方法抛出异常，则按照order的顺序降序执行完所有Aspect的afterThrowing方法
    private void invokeAfterThrowingAdvides(Method method, Object[] args, Exception e) throws Throwable {
        for (int i =  sortedAspectInfoList.size() - 1; i >=0 ; i--){
            sortedAspectInfoList.get(i).getAspectObject().afterThrowing(targetClass, method, args, e);
        }
    }

    //3.如果被代理方法正常返回，则按照order的顺序降序执行完所有Aspect的afterReturning方法
    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable {
        Object result = null;
        // 由于是后置方法，所以按照降序来执行
        for (int i =  sortedAspectInfoList.size() - 1; i >=0 ; i--){
            result = sortedAspectInfoList.get(i).getAspectObject().afterReturning(targetClass, method, args, returnValue);
        }
        return result;
    }

    //1.按照order的顺序升序执行完所有Aspect的before方法
    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {
        for(AspectInfo aspectInfo : sortedAspectInfoList){
            aspectInfo.getAspectObject().before(targetClass, method, args);
        }
    }
}
