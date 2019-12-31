package com.haimin.li.spring.annotation.config;

import com.haimin.li.spring.annotation.pojo.Person;
import org.springframework.context.annotation.*;

@Configuration
public class BeanAnnotationConfig {

    /*
     * prototype：多实例的：ioc容器启动并不会去调用方法创建对象放在容器中。
     * 					每次获取的时候才会调用方法创建对象；
     * singleton：单实例的（默认值）：ioc容器启动会调用方法创建对象放到ioc容器中。
     * 			以后每次获取就是直接从容器（map.get()）中拿，
     * request：同一次请求创建一个实例 ----- web应用
     * session：同一个session创建一个实例 ----- web应用
     */
    @Scope("singleton") //默认是单实例的，创建IOC容器的时候创建对象，并注册组件
//    @Scope("prototype") //创建IOC容器的时候不创建对象，在使用到组件的时候创建并注册
    @Bean
    public Person person(){
        System.out.println("创建 Person");
        return new Person(23,"wangWang","dog");
    }

    @Lazy //延迟创建bean，用到是时候再创建
    @Primary//存在多个相同类型的组件的时候，优先注入这个bean
    @Bean
    public Person personLazy(){
        System.out.println("创建 Person");
        return new Person(33,"wangWangLazy","cat");
    }

    /*
     * value 别名，可以取多个别名
     */
    @DependsOn(value = {
            "personLazy",
            "person"
    })// 创建这个bean之前，一定要有 personLazy， person这两个组件创建完成
    @Bean(value = {"dog", "dog1111", "dog2222"}, initMethod = "myWork", destroyMethod = "myDream")
    public Person person1(){
        System.out.println("创建 Person");
        return new Person(11,"wang","dog_wang");
    }
}
