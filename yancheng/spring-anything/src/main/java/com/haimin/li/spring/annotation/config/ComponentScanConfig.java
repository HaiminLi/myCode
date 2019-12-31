package com.haimin.li.spring.annotation.config;


import com.haimin.li.spring.annotation.service.PersonServer;
import com.haimin.li.spring.annotation.tool.MyFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

//配置类==配置文件
@Configuration //告诉Spring这是一个配置类
@ComponentScan(value = "com.haimin.li.spring.annotation", useDefaultFilters = false,
        includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class}),
        @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,classes={PersonServer.class}),
        @ComponentScan.Filter(type=FilterType.CUSTOM,classes={MyFilter.class})
})
public class ComponentScanConfig {

    /*
     * @ComponentScan 用法
     * value:指定要扫描的包
     * excludeFilters = Filter[] ：指定扫描的时候按照什么规则排除那些组件
     * includeFilters = Filter[] ：指定扫描的时候只需要包含哪些组件  需要设置 useDefaultFilters = false，不然不起作用,这些条件是‘或’的关系
     * FilterType.ANNOTATION：按照注解
     * FilterType.ASSIGNABLE_TYPE：按照给定的类型；
     * FilterType.ASPECTJ：使用ASPECTJ表达式 【不常用】
     * FilterType.REGEX：使用正则指定 【不常用】
     * FilterType.CUSTOM：使用自定义规则
     */
}
