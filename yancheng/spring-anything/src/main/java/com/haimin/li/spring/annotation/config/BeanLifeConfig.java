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
//        System.out.println("���� Person");
        return new Pink();
    }

    @Bean(initMethod = "myWork")
    public Person person(){
//        System.out.println("���� Person");
        Person person = new Person();
        person.setAge(122);
        person.setName("��ǿ");
        person.setWork("��ְ̨Ա");
        return person;
    }

}
