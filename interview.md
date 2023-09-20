# 一、IOC相关问题

## **如何实现自研框架的IOC?**

具体实现流程为：

1. 定义与IOC相关的注解Component、Controller、Service以及Repository，用于标记需要被BeanContainer管理的类； 

2. 创建BeanContainer，提取指定范围内的所有被注解标记的类并加载进容器中，可用ConcurrentHashMap进行保存，键代表大Class对象，值代表对应的实例； 

3.  BeanContainer容器的操作方式，主要涉及到容器的增删改查操作；

4.  由于实例里面某些必须的成员变量还没有被创建出来，因此还需进行依赖注入； 

5. 定义依赖注入相关的注解标签Autowired，被此标签标记的成员变量在Spring初始化时，需要被容器创建对应的实例，并将其注入到成员变量里；

具体实现逻辑为：

- 遍历 Bean 容器中所有的     Class 对象
- 遍历 Class 对象的所有成员变量
- 找出被 Autowired 标记的成员变量
- 获取这些成员变量的类型
- 获取这些成员变量的类型在容器里对应的实例
- 通过反射将对应的成员变量实例注入到成员变量所在类的实例里

至此，就实现了IOC容器的创建以及依赖注入；

## 为什么要使用IOC？

## 如何解决循环依赖问题？

# 二、AOP相关问题

## **如何实现自研框架的AOP?**

具体实现流程为：

1. 定义与横切逻辑相关的注解Aspect和Order，用于标记切面类，以及切面类的执行顺序；

2. 定义供外部使用的横切逻辑骨架，骨架中包含before, afterReturning以及afterThrowing等未实现的方法（此处用到了代理模式）；

3. 创建 MethodInterceptor 的实现类 AspectListExecutor，按照 Order 对 Aspect 标记的切面类进行排序，并实现横切逻辑intercept以及被代理对象方法的定序执行；

4. 织入横切逻辑生成动态代理对象的实现类，替换BeanContainer中对应类的实例；

使用Cglib动态代理实现的AOP1.0的不足地方：

- Aspect 标记的横切类只支持对某个标签标记的类进行横切逻辑的织入
- 需要披上 AspectJ 的外衣

**引入AspectJ的AOP 2.0：**

- 引入 AspectJ的切面表达式和相关的定位解析机制，使用pointcut 可以让切面逻辑的织入更加灵活；

## 为什么要使用AOP？

## JDK动态代理和Cglib(Code Generation Library) 动态代理的区别？

jdk只能代理接口或者接口的实现类，然后去调接口的实现方法；

cglib类似子类继承，可以代理没实现接口的类，其实就是在子类中调用父类方法；

JDK 动态代理：

- 并没有实际的 class 文件，而是在程序运行时生成类的字节码文件并加载到 JVM 中
- 要求被代理的类必须实现接口
- 并不要求代理对象去实现接口，所以可以复用代理对象的逻辑

Cglib(Code Generation Library) 动态代理：

- 不要求被代理类实现接口
- 内部主要封装了ASM Java字节码操控框架
- 动态生成子类以覆盖非 final 的方法，绑定钩子回调自定义拦截器

## pointcut是怎么用的，为什么使用pointcut可以让切面逻辑的织入更加灵活？

## 什么是AOP，和OOP的区别是什么？

# 三、MVC相关问题

## **如何实现自研框架的MVC?**

具体实现流程为：

1. 创建实现HttpServlet的中央调度器DispatcherServlet拦截所有请求，在init方法中对容器进行初始化，以及初始化请求处理器责任链，以责任链的模式执行注册的请求处理器；在service方法中顺序执行请求处理器责任链中的请求，并对处理结果进行渲染；

2. 请求处理器主要包括PreRequestProcessor进行请求预处理，StaticResourceRequestProcessor进行静态资源请求处理，JspRequestProcessor对访问jsp资源的请求进行处理，以及最关键的ControllerRequestProcessor，负责针对特定请求，解析请求里的参数及对应的值，并赋值给Controller方法的参数，选择匹配的Controller方法进行处理，同时选择合适的Render，为后续请求处理结果的渲染做准备；

3. 对结果进行渲染相关的类ResultRender，主要包括内部异常渲染器InternalErrorResultRender，资源找不到时使用的渲染器ResourceNotFoundResultRender，Json渲染器JsonResultRender，以及页面渲染器ViewResultRender，将请求处理器处理完后的结果显示到指定的视图上；

## 为什么要使用MVC架构？

# 四、Spring相关问题

## **Spring核心模块有哪些？**

1. spring-core

   包含框架基本的核心工具类，其他组件都要使用到这个包里的类；

   定义并提供资源的访问方式；

2. spring-beans 

   Spring主要面向Bean编程（BOP）；

   包含Bean的定义、解析、创建；

3. spring-context 

   为Spring提供运行时环境，保存对象的状态；

   扩展了BeanFactory；

4. spring-aop 

   最小化的动态代理实现；

   JDK动态代理；

   Cglib；

   只能使用运行时织入，仅支持方法级编织，仅支持方法执行切入点；

## Spring中，有几种织入切面逻辑的方式？