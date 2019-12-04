//package com.social.commerce.member.configs;
//
//import com.xxl.job.core.executor.XxlJobExecutor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by xinpf on 2019/6/26.
// */
//@Configuration
//@RefreshScope
//@Slf4j
//@ComponentScan(basePackages = "com.social.commerce.commission.jobs")
//public class XxlJobConfig {
//
//  @Value("${xxl.job.admin.addresses}")
//  private String adminAddresses;
//
//  @Value("${xxl.job.executor.appname}")
//  private String appName;
//
//  @Value("${xxl.job.executor.ip}")
//  private String ip;
//
//  @Value("${xxl.job.executor.port}")
//  private int port;
//
//  @Value("${xxl.job.accessToken}")
//  private String accessToken;
//
//  @Value("${xxl.job.executor.logpath}")
//  private String logPath;
//
//  @Value("${xxl.job.executor.logretentiondays}")
//  private int logRetentionDays;
//
//
//  @Bean(initMethod = "start", destroyMethod = "destroy")
//  public XxlJobExecutor xxlJobExecutor() {
//    log.info("------------ xxlJobExecutor -----------");
//    XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
//    xxlJobExecutor.setIp(ip);
//    xxlJobExecutor.setPort(port);
//    xxlJobExecutor.setAppName(appName);
//    xxlJobExecutor.setAdminAddresses(adminAddresses);
//    xxlJobExecutor.setLogPath(logPath);
//    //xxlJobExecutor.setAccessToken(accessToken);
//    return xxlJobExecutor;
//  }
//
//
//}
