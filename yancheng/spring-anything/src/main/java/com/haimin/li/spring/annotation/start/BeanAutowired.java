package com.haimin.li.spring.annotation.start;

import com.haimin.li.spring.annotation.config.BeanAnnotationConfig;
import com.haimin.li.spring.annotation.config.BeanAutowiredConfig;
import com.haimin.li.spring.annotation.pojo.Person;
import com.haimin.li.spring.annotation.pojo.Red;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanAutowired {

    /**
     * IOC ע��
     * 1����@Autowired���Զ�ע�룺
     * 		1����Ĭ�����Ȱ�������ȥ�������Ҷ�Ӧ�����:applicationContext.getBean(BookDao.class);�ҵ��͸�ֵ
     * 		2��������ҵ������ͬ���͵�������ٽ����Ե�������Ϊ�����idȥ�����в���
     * 							applicationContext.getBean("bookDao")
     * 		3����@Qualifier("bookDao")��ʹ��@Qualifierָ����Ҫװ��������id��������ʹ��������
     * 		4�����Զ�װ��Ĭ��һ��Ҫ�����Ը�ֵ�ã�û�оͻᱨ��
     * 			����ʹ��@Autowired(required=false);
     * 		5����@Primary����Spring�����Զ�װ���ʱ��Ĭ��ʹ����ѡ��bean��
     * 				Ҳ���Լ���ʹ��@Qualifierָ����Ҫװ���bean������
     * 		BookService{
     * 			@Autowired
     * 			BookDao  bookDao;
     * 		}
     *
     * 2����Spring��֧��ʹ��@Resource(JSR250)��@Inject(JSR330)[java�淶��ע��]
     * 		@Resource:
     * 			���Ժ�@Autowiredһ��ʵ���Զ�װ�书�ܣ�Ĭ���ǰ���������ƽ���װ��ģ�
     * 			û����֧��@Primary����û��֧��@Autowired��reqiured=false��;
     * 		@Inject:
     * 			��Ҫ����javax.inject�İ�����Autowired�Ĺ���һ����û��required=false�Ĺ��ܣ�
     *  @Autowired:Spring����ģ� @Resource��@Inject����java�淶
     *
     *    AutowiredAnnotationBeanPostProcessor:��������Զ�װ�书�ܣ�
     *
     * 3���� @Autowired:�����������������������ԣ����Ǵ������л�ȡ���������ֵ
     * 		1����[��ע�ڷ���λ��]��@Bean+���������������������л�ȡ;Ĭ�ϲ�д@AutowiredЧ����һ���ģ������Զ�װ��
     * 		2����[���ڹ�������]��������ֻ��һ���вι�����������вι�������@Autowired����ʡ�ԣ�����λ�õ�������ǿ����Զ��������л�ȡ
     * 		3�������ڲ���λ�ã�
     *
     * 4�����Զ��������Ҫʹ��Spring�����ײ��һЩ�����ApplicationContext��BeanFactory��xxx����
     * 		�Զ������ʵ��xxxAware���ڴ��������ʱ�򣬻���ýӿڹ涨�ķ���ע����������Aware��
     * 		��Spring�ײ�һЩ���ע�뵽�Զ����Bean�У�
     * 		xxxAware������ʹ��xxxProcessor��
     * 			ApplicationContextAware==��ApplicationContextAwareProcessor��
     *
     * ����ע�룺 ʹ��@PropertySource��ȡ�ⲿ�����ļ��е�k/v���浽���еĻ���������;�������ⲿ�������ļ��Ժ�ʹ��${}ȡ�������ļ���ֵ
     *
     *
     */
    public static void main(String[] args) {
        beanAutowired();
    }

    //1��ʹ�������ж�̬����: �����������λ�ü��� -Dspring.profiles.active=test
    //2������ķ�ʽ����ĳ�ֻ�����

    private static void beanAutowired(){
        //1������ioc����
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //1������һ��applicationContext
        //2��������Ҫ����Ļ���
        applicationContext.getEnvironment().setActiveProfiles("dev");
        //3��ע����������
        applicationContext.register(BeanAnnotationConfig.class);
        //4������ˢ������
        applicationContext.refresh();
        System.out.println("�����������...");
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
