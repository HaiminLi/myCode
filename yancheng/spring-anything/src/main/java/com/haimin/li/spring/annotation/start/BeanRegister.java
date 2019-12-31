package com.haimin.li.spring.annotation.start;


import com.haimin.li.spring.annotation.config.*;
import com.haimin.li.spring.annotation.pojo.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SuppressWarnings("All")
public class BeanRegister {
    /**
     * ��������ע�������
     * 1������ɨ��+�����עע�⣨@Controller/@Service/@Repository/@Component��[�Լ�д����]
     * 2����@Bean[����ĵ���������������]
     * 3����@Import[���ٸ������е���һ�����]
     * 		1����@Import(Ҫ���뵽�����е����)�������оͻ��Զ�ע����������idĬ����ȫ����
     * 		2����ImportSelector:������Ҫ����������ȫ�������飻
     * 		3����ImportBeanDefinitionRegistrar:�ֶ�ע��bean��������
     * 4����ʹ��Spring�ṩ�� FactoryBean������Bean��;
     * 		1����Ĭ�ϻ�ȡ�����ǹ���bean����getObject�����Ķ���
     * 		2����Ҫ��ȡ����Bean����������Ҫ��idǰ���һ��&
     * 			&colorFactoryBean
     */

    public static void main(String[] args) {
//        componentScan();
//        beanAnnotation();
//        conditional();
//        importAnnotation();
        springFactoryBean();
    }

    private static void springFactoryBean(){
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(FactoryBeanConfig.class);
        System.out.println("__________IOC�����������__________ ");
        Object createBeanByFactory1 = applicationContext.getBean("createBeanByFactory");
        System.out.println(createBeanByFactory1);
        Object createBeanByFactory2 = applicationContext.getBean("&createBeanByFactory");
        System.out.println(createBeanByFactory2);
    }

    private static void importAnnotation(){
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(importAnnotationConfig.class);
        System.out.println("__________IOC�����������__________ ");
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }

    private static void conditional(){
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ConditionalConfig.class);
        System.out.println("__________IOC�����������__________ ");
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }

    private static void beanAnnotation(){
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(BeanAnnotationConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
//        Person bean1 = applicationContext.getBean(Person.class);
//        System.out.println(bean1);
        System.out.println("__________IOC�����������__________ ");
        Person bean2 = (Person) applicationContext.getBean("dog1111");
        Person bean1 = (Person) applicationContext.getBean("dog");
        System.out.println(bean2);
        System.out.println(bean1 == bean2);
//        for (String name : definitionNames) {
//            System.out.println(name);
//        }
    }

    private static void componentScan(){
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        String id = applicationContext.getId();
        System.out.println(id);
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }
}
