package com.haimin.li.spring.annotation.config;

import com.haimin.li.spring.annotation.pojo.Person;
import com.haimin.li.spring.annotation.pojo.Pink;
import com.haimin.li.spring.annotation.pojo.Red;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.haimin.li.spring.annotation.pojo")
@PropertySource(value={"classpath:/person.properties"})
public class BeanAutowiredConfig {

//    @Profile("test")
    @Bean
    public Red myBean(@Autowired Person person){

        Red red = new Red();
        red.setPerson(person);
        return red;
    }

//    @Profile("dev")
    @Bean
    public Person person(){
//        System.out.println("创建 Person");
        Person person = new Person();
        person.setAge(122);
        person.setName("王强");
        person.setWork("柜台职员");
        return person;
    }
}
