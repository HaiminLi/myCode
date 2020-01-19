package com.haimin.li.spring.annotation.start;

import com.haimin.li.spring.annotation.config.BeanAnnotationConfig;
import com.haimin.li.spring.annotation.config.BeanAutowiredConfig;
import com.haimin.li.spring.annotation.pojo.Person;
import com.haimin.li.spring.annotation.pojo.Red;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanAutowired {

    /**
     * IOC 注入
     * 1）、@Autowired：自动注入：
     * 		1）、默认优先按照类型去容器中找对应的组件:applicationContext.getBean(BookDao.class);找到就赋值
     * 		2）、如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找
     * 							applicationContext.getBean("bookDao")
     * 		3）、@Qualifier("bookDao")：使用@Qualifier指定需要装配的组件的id，而不是使用属性名
     * 		4）、自动装配默认一定要将属性赋值好，没有就会报错；
     * 			可以使用@Autowired(required=false);
     * 		5）、@Primary：让Spring进行自动装配的时候，默认使用首选的bean；
     * 				也可以继续使用@Qualifier指定需要装配的bean的名字
     * 		BookService{
     * 			@Autowired
     * 			BookDao  bookDao;
     * 		}
     *
     * 2）、Spring还支持使用@Resource(JSR250)和@Inject(JSR330)[java规范的注解]
     * 		@Resource:
     * 			可以和@Autowired一样实现自动装配功能；默认是按照组件名称进行装配的；
     * 			没有能支持@Primary功能没有支持@Autowired（reqiured=false）;
     * 		@Inject:
     * 			需要导入javax.inject的包，和Autowired的功能一样。没有required=false的功能；
     *  @Autowired:Spring定义的； @Resource、@Inject都是java规范
     *
     *    AutowiredAnnotationBeanPostProcessor:解析完成自动装配功能；
     *
     * 3）、 @Autowired:构造器，参数，方法，属性；都是从容器中获取参数组件的值
     * 		1）、[标注在方法位置]：@Bean+方法参数；参数从容器中获取;默认不写@Autowired效果是一样的；都能自动装配
     * 		2）、[标在构造器上]：如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略，参数位置的组件还是可以自动从容器中获取
     * 		3）、放在参数位置：
     *
     * 4）、自定义组件想要使用Spring容器底层的一些组件（ApplicationContext，BeanFactory，xxx）；
     * 		自定义组件实现xxxAware；在创建对象的时候，会调用接口规定的方法注入相关组件；Aware；
     * 		把Spring底层一些组件注入到自定义的Bean中；
     * 		xxxAware：功能使用xxxProcessor；
     * 			ApplicationContextAware==》ApplicationContextAwareProcessor；
     *
     * 属性注入： 使用@PropertySource读取外部配置文件中的k/v保存到运行的环境变量中;加载完外部的配置文件以后使用${}取出配置文件的值
     *
     *
     */
    public static void main(String[] args) {
        beanAutowired();
    }

    //1、使用命令行动态参数: 在虚拟机参数位置加载 -Dspring.profiles.active=test
    //2、代码的方式激活某种环境；

    private static void beanAutowired(){
        //1、创建ioc容器
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //1、创建一个applicationContext
        //2、设置需要激活的环境
        applicationContext.getEnvironment().setActiveProfiles("dev");
        //3、注册主配置类
        applicationContext.register(BeanAnnotationConfig.class);
        //4、启动刷新容器
        applicationContext.refresh();
        System.out.println("容器创建完成...");
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        Person bean = applicationContext.getBean(Person.class);
        Red bean1 = applicationContext.getBean(Red.class);
        System.out.println(bean);
        System.out.println(bean1);
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }
}
