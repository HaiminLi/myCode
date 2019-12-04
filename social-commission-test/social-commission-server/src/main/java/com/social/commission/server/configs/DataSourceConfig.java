package com.social.commission.server.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


/**
 * @author Haimin Li
 * @description:
 * @date 2019/7/11  19:33
 */
//@Configuration
public class DataSourceConfig {
//    @Bean(name = "master")
//    @Primary
//    @ConfigurationProperties(prefix = "jdbc.master") // application.properteis中对应属性的前缀
//    public DataSource dataSource1() {
//        return DataSourceBuilder.create().build();
//    }
}
