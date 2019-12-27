package com.social.commission.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(value = {"com.social.commission"})
@EnableFeignClients
public class SocialCommissionServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialCommissionServerApplication.class, args);
	}
}

/*
 * @SpringBootApplication ：Spring Boot应用标注在某个类上说明这个类是SpringBoot的主配置类，SpringBoot就应该运行这个类的main方法来启动SpringBoot应用
 * 注解的定义：
   @Target(ElementType.TYPE)
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   @Inherited
   @SpringBootConfiguration  标注在某个类上，表示这是一个Spring Boot的配置类
   @EnableAutoConfiguration  开启自动配置功能
   @ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
   									 @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
   public @interface SpringBootApplication{}
 *
 *
 */
//这两个注解都包含四个源注解
 /*
 * 介绍注解 @EnableAutoConfiguration
    @Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Inherited
	@AutoConfigurationPackage  将主配置类（@SpringBootApplication标注的类）所在包及下面所有子包里面的所有组件扫描到Spring容器
	@Import(AutoConfigurationImportSelector.class)
	public @interface EnableAutoConfiguration{}
 *
 * @Import(AutoConfigurationImportSelector.class) 给容器中导入组件，由此类定义
 * AutoConfigurationImportSelector 类下面的方法
 * public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //判断 enableautoconfiguration注解有没有开启，默认开启（是否进行自动装配）
        if (!isEnabled(annotationMetadata)) {
            return NO_IMPORTS;
        }
        //第一步：加载配置文件META-INF/spring-autoconfigure-metadata.properties，从中获取所有支持自动配置的信息
        AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader
                .loadMetadata(this.beanClassLoader);
        //获取EnableAutoConfiguration的属性，也就是exclue和excludeName的内容
        AnnotationAttributes attributes = getAttributes(annotationMetadata);
         //第二步：读取META-INF/spring.factories下的EnableAutoConfiguration的配置
        List<String> configurations = getCandidateConfigurations(annotationMetadata,
                attributes);
        //去除重复的配置类，若我们自己写的starter 可能存在重复的
        configurations = removeDuplicates(configurations);
        //如果项目中某些自动配置类，我们不希望其自动配置，我们可以通过EnableAutoConfiguration的exclude或excludeName属性进行配置，或者也可以在配置文件里通过配置项“spring.autoconfigure.exclude”进行配置。
        //找到不希望自动配置的配置类（根据EnableAutoConfiguration注解的一个exclusions属性）
        Set<String> exclusions = getExclusions(annotationMetadata, attributes);
        //校验排除类（exclusions指定的类必须是自动配置类，否则抛出异常）
        checkExcludedClasses(configurations, exclusions);
        //删除所有不希望自动配置的配置类
        configurations.removeAll(exclusions);
        //第三步：根据OnClassCondition（注解中配置的当存在某类才生效）注解过滤掉一些条件没有满足的
        configurations = filter(configurations, autoConfigurationMetadata);// 现在已经找到所有需要被应用的候选配置类
         //第四步：广播AutoConfigurationImportEvents事件
        fireAutoConfigurationImportEvents(configurations, exclusions);
        return StringUtils.toStringArray(configurations);
    }
 *
 **/

 /*
  * 介绍注解 @AutoConfigurationPackage 自动配置包
    @Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Inherited
	@Import(AutoConfigurationPackages.Registrar.class)
	public @interface AutoConfigurationPackage {
	}
  * AutoConfigurationPackages.Registrar 类：得到spring boot 主配置类所在的包
  * 将主配置类（@SpringBootApplication标注的类）所在包及下面所有子包里面的所有组件扫描到Spring容器
  */


