package com.social.commission.server.configs;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author Haimin Li
 * @description: TODO
 * @date 2019/7/16  10:45
 */
//@Configuration
//@MapperScan(basePackages = {"com.social.commission.server.dao"}, sqlSessionFactoryRef = "sqlSessionFactoryMaster")
public class MybatisDbWriteConfig {
//    @Autowired
//    @Qualifier("master")
//    private DataSource dataSource;
//
//    @Bean
//    @Primary
//    public SqlSessionFactory sqlSessionFactoryMaster() throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(dataSource); // 使用master数据源, 连接写库
//        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/*.xml"));
//        return factoryBean.getObject();
//    }
//    @Bean
//    @Primary
//    public SqlSessionTemplate sqlSessionTemplate1() throws Exception {
//        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryMaster()); // 使用上面配置的Factory
//        return template;
//    }
}
