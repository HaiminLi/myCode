package com.haimin.li.spring.annotation.start;

import com.haimin.li.spring.annotation.aop.MathCalculator;
import com.haimin.li.spring.annotation.config.AOPConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AOPTest {

    public static void main(String[] args) {
        //1、创建ioc容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AOPConfig.class);
        System.out.println("容器创建完成...");
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);

        mathCalculator.div(1, 0);
        //关闭容器
        applicationContext.close();
    }
}
