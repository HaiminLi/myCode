package com.haimin.li.spring.annotation.config;


import com.haimin.li.spring.annotation.condition.LinuxCondition;
import com.haimin.li.spring.annotation.pojo.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalConfig {

    /**
     * @Conditional({Condition}) ： 按照一定的条件进行判断，满足条件给容器中注册bean
     *
     * 如果LinuxCondition 返回true，则注册组件，否则不注册
     */

    @Conditional(LinuxCondition.class)
    @Bean("bill")
    public Person person01(){
        return new Person(33,"Bill Gates","cat");
    }

    @Conditional(LinuxCondition.class)
    @Bean("linus")
    public Person person02(){
        return new Person(33,"Linus","cat");
    }
}
