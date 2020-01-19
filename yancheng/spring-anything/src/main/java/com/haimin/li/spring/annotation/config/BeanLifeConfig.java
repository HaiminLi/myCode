package com.haimin.li.spring.annotation.config;

import com.haimin.li.spring.annotation.pojo.Person;
import com.haimin.li.spring.annotation.pojo.Pink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.haimin.li.spring.annotation.pojo")
public class BeanLifeConfig {

    @Bean(initMethod = "init", destroyMethod = "detory")
    public Pink myBean(){
//        System.out.println("创建 Person");
        return new Pink();
    }

    @Bean(initMethod = "myWork")
    public Person person(){
//        System.out.println("创建 Person");
        Person person = new Person();
        person.setAge(122);
        person.setName("王强");
        person.setWork("柜台职员");
        return person;
    }

}
