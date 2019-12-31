package com.haimin.li.spring.annotation.config;

import com.haimin.li.spring.annotation.pojo.FactoryBeanImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryBeanConfig {

    @Bean
    public FactoryBeanImpl createBeanByFactory(){
        return new FactoryBeanImpl();
    }
}
