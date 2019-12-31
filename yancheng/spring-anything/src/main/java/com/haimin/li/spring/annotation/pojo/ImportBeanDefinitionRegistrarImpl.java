package com.haimin.li.spring.annotation.pojo;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class ImportBeanDefinitionRegistrarImpl implements ImportBeanDefinitionRegistrar {

    /**
     * AnnotationMetadata����ǰ���ע����Ϣ
     * BeanDefinitionRegistry:BeanDefinitionע���ࣻ
     * 		��������Ҫ��ӵ������е�bean������
     * 		BeanDefinitionRegistry.registerBeanDefinition�ֹ�ע�����
     */
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //�Ƿ���ڸ�����bean
        boolean definition = registry.containsBeanDefinition("com.haimin.li.spring.annotation.pojo.Color");
        if(definition){
            //ָ��Bean������Ϣ����Bean�����ͣ�Bean��������
            RootBeanDefinition beanDefinition = new RootBeanDefinition(Black.class);
            //ע��һ��Bean��ָ��bean��
            registry.registerBeanDefinition("black", beanDefinition);
        }
    }
}
