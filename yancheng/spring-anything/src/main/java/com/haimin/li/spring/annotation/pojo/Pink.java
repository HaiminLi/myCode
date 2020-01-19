package com.haimin.li.spring.annotation.pojo;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Pink implements InitializingBean, DisposableBean {

    public Pink() {
        System.out.println("构造器");
    }

    public void init(){
        System.out.println("myBean初始化（）");
    }
    public void detory(){
        System.out.println("myBean销毁（）");
    }

    public void destroy() throws Exception {
        System.out.println("myBean 实现接口销毁（）");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("myBean 实现接口方法 afterPropertiesSet（）");
    }
    @PostConstruct
    public void initAnnotation(){
        System.out.println("myBean 注解版初始化（）");
    }
    @PreDestroy
    public void detoryAnnotation(){
        System.out.println("myBean 注解版销毁（）");
    }
}
