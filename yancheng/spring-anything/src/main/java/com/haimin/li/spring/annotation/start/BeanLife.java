package com.haimin.li.spring.annotation.start;

import com.haimin.li.spring.annotation.config.BeanLifeConfig;
import com.haimin.li.spring.annotation.config.FactoryBeanConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SuppressWarnings("All")
public class BeanLife {

    /**
     *  1����ָ����ʼ�������ٷ�����
     *  		ͨ��@Beanָ��init-method��destroy-method��
     *  2����ͨ����Beanʵ�� �ӿ� InitializingBean�������ʼ���߼�����
     *   				       DisposableBean�����������߼���;
     *  3��������ʹ��JSR250��ע���
     *   		@PostConstruct ����bean������ɲ������Ը�ֵ��ɣ���ִ�г�ʼ������
     *  		@PreDestroy�� ����������bean֮ǰ֪ͨ���ǽ���������
     *  4����BeanPostProcessor��interface����bean�ĺ��ô�������
     *   		��bean��ʼ��ǰ�����һЩ��������
     *   		postProcessBeforeInitialization:�ڳ�ʼ��֮ǰ����
     *   		postProcessAfterInitialization:�ڳ�ʼ��֮����
     *
     *
     * bean���������ڣ� bean����---��ʼ��----���ٵĹ���
     * bean���� :
     *  		��ʵ����������������ʱ�򴴽�����
     *   		��ʵ������ÿ�λ�ȡ��ʱ�򴴽�����
     *
     *  ��ʼ����(myBean�ĺ��ô����� Before -> myBeanע����ʼ������ -> myBeanʵ�ֽӿڷ��� afterPropertiesSet���� -> myBean��ʼ������ -> myBean�ĺ��ô����� After)
     *
     * bean���٣� (myBeanע������٣��� -> myBeanʵ�ֽӿ����٣��� -> myBean���٣��� -> ����bean)
     *   		��ʵ���������رյ�ʱ��
     *   		��ʵ������������������bean����������������ٷ�����
     */

    public static void main(String[] args) {
        beanLifeConfig();
    }

    private static void beanLifeConfig(){
        //1������ioc����
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanLifeConfig.class);
        System.out.println("�����������...");
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
        //applicationContext.getBean("car");
        //�ر�����
        applicationContext.close();
    }
}
