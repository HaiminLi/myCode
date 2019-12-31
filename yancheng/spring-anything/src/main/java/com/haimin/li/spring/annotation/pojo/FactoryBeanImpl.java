package com.haimin.li.spring.annotation.pojo;

import org.springframework.beans.factory.FactoryBean;

public class FactoryBeanImpl implements FactoryBean<White> {
    public White getObject() throws Exception {
        //返回一个对象，这个对象会添加到容器中
        return new White();
    }

    public Class<?> getObjectType() {
        //返回对象的类型
        return White.class;
    }
    //FactoryBean 接口的默认方法，默认返回true，是否单例
    // default boolean isSingleton() {
    //		return true;
    //	}
}
