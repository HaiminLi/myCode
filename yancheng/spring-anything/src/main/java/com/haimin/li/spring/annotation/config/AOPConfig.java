package com.haimin.li.spring.annotation.config;

import com.haimin.li.spring.annotation.aop.LogAspects;
import com.haimin.li.spring.annotation.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
public class AOPConfig {

    //ҵ���߼������������
    @Bean
    public MathCalculator calculator(){
        return new MathCalculator();
    }

    //��������뵽������
    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
