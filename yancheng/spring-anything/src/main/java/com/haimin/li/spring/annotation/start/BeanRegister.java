package com.haimin.li.spring.annotation.start;


import com.haimin.li.spring.annotation.config.*;
import com.haimin.li.spring.annotation.pojo.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SuppressWarnings("All")
public class BeanRegister {
    /**
     * 给容器中注册组件；
     * 1）、包扫描+组件标注注解（@Controller/@Service/@Repository/@Component）[自己写的类]
     * 2）、@Bean[导入的第三方包里面的组件]
     * 3）、@Import[快速给容器中导入一个组件]
     * 		1）、@Import(要导入到容器中的组件)；容器中就会自动注册这个组件，id默认是全类名
     * 		2）、ImportSelector:返回需要导入的组件的全类名数组；
     * 		3）、ImportBeanDefinitionRegistrar:手动注册bean到容器中
     * 4）、使用Spring提供的 FactoryBean（工厂Bean）;
     * 		1）、默认获取到的是工厂bean调用getObject创建的对象
     * 		2）、要获取工厂Bean本身，我们需要给id前面加一个&
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
        System.out.println("__________IOC容器创建完成__________ ");
        Object createBeanByFactory1 = applicationContext.getBean("createBeanByFactory");
        System.out.println(createBeanByFactory1);
        Object createBeanByFactory2 = applicationContext.getBean("&createBeanByFactory");
        System.out.println(createBeanByFactory2);
    }

    private static void importAnnotation(){
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(importAnnotationConfig.class);
        System.out.println("__________IOC容器创建完成__________ ");
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }

    private static void conditional(){
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ConditionalConfig.class);
        System.out.println("__________IOC容器创建完成__________ ");
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
        System.out.println("__________IOC容器创建完成__________ ");
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
