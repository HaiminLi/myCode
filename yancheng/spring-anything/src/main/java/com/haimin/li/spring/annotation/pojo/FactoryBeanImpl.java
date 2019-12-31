package com.haimin.li.spring.annotation.pojo;

import org.springframework.beans.factory.FactoryBean;

public class FactoryBeanImpl implements FactoryBean<White> {
    public White getObject() throws Exception {
        //����һ����������������ӵ�������
        return new White();
    }

    public Class<?> getObjectType() {
        //���ض��������
        return White.class;
    }
    //FactoryBean �ӿڵ�Ĭ�Ϸ�����Ĭ�Ϸ���true���Ƿ���
    // default boolean isSingleton() {
    //		return true;
    //	}
}
