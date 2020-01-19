package com.haimin.li.spring.annotation.pojo;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Pink implements InitializingBean, DisposableBean {

    public Pink() {
        System.out.println("������");
    }

    public void init(){
        System.out.println("myBean��ʼ������");
    }
    public void detory(){
        System.out.println("myBean���٣���");
    }

    public void destroy() throws Exception {
        System.out.println("myBean ʵ�ֽӿ����٣���");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("myBean ʵ�ֽӿڷ��� afterPropertiesSet����");
    }
    @PostConstruct
    public void initAnnotation(){
        System.out.println("myBean ע����ʼ������");
    }
    @PreDestroy
    public void detoryAnnotation(){
        System.out.println("myBean ע������٣���");
    }
}
