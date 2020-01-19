package com.haimin.li.spring.annotation.start;

import com.haimin.li.spring.annotation.config.BeanLifeConfig;
import com.haimin.li.spring.annotation.config.FactoryBeanConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SuppressWarnings("All")
public class BeanLife {

    /**
     *  1）、指定初始化和销毁方法；
     *  		通过@Bean指定init-method和destroy-method；
     *  2）、通过让Bean实现 接口 InitializingBean（定义初始化逻辑），
     *   				       DisposableBean（定义销毁逻辑）;
     *  3）、可以使用JSR250；注解版
     *   		@PostConstruct ：在bean创建完成并且属性赋值完成；来执行初始化方法
     *  		@PreDestroy： 在容器销毁bean之前通知我们进行清理工作
     *  4）、BeanPostProcessor【interface】：bean的后置处理器；
     *   		在bean初始化前后进行一些处理工作；
     *   		postProcessBeforeInitialization:在初始化之前工作
     *   		postProcessAfterInitialization:在初始化之后工作
     *
     *
     * bean的生命周期： bean创建---初始化----销毁的过程
     * bean创建 :
     *  		单实例：在容器启动的时候创建对象
     *   		多实例：在每次获取的时候创建对象
     *
     *  初始化：(myBean的后置处理器 Before -> myBean注解版初始化（） -> myBean实现接口方法 afterPropertiesSet（） -> myBean初始化（） -> myBean的后置处理器 After)
     *
     * bean销毁： (myBean注解版销毁（） -> myBean实现接口销毁（） -> myBean销毁（） -> 销毁bean)
     *   		单实例：容器关闭的时候
     *   		多实例：容器不会管理这个bean；容器不会调用销毁方法；
     */

    public static void main(String[] args) {
        beanLifeConfig();
    }

    private static void beanLifeConfig(){
        //1、创建ioc容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanLifeConfig.class);
        System.out.println("容器创建完成...");
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
        //applicationContext.getBean("car");
        //关闭容器
        applicationContext.close();
    }
}
