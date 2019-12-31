package com.haimin.li.spring.annotation.config;

import com.haimin.li.spring.annotation.pojo.Person;
import org.springframework.context.annotation.*;

@Configuration
public class BeanAnnotationConfig {

    /*
     * prototype����ʵ���ģ�ioc��������������ȥ���÷�������������������С�
     * 					ÿ�λ�ȡ��ʱ��Ż���÷�����������
     * singleton����ʵ���ģ�Ĭ��ֵ����ioc������������÷�����������ŵ�ioc�����С�
     * 			�Ժ�ÿ�λ�ȡ����ֱ�Ӵ�������map.get()�����ã�
     * request��ͬһ�����󴴽�һ��ʵ�� ----- webӦ��
     * session��ͬһ��session����һ��ʵ�� ----- webӦ��
     */
    @Scope("singleton") //Ĭ���ǵ�ʵ���ģ�����IOC������ʱ�򴴽����󣬲�ע�����
//    @Scope("prototype") //����IOC������ʱ�򲻴���������ʹ�õ������ʱ�򴴽���ע��
    @Bean
    public Person person(){
        System.out.println("���� Person");
        return new Person(23,"wangWang","dog");
    }

    @Lazy //�ӳٴ���bean���õ���ʱ���ٴ���
    @Primary//���ڶ����ͬ���͵������ʱ������ע�����bean
    @Bean
    public Person personLazy(){
        System.out.println("���� Person");
        return new Person(33,"wangWangLazy","cat");
    }

    /*
     * value ����������ȡ�������
     */
    @DependsOn(value = {
            "personLazy",
            "person"
    })// �������bean֮ǰ��һ��Ҫ�� personLazy�� person����������������
    @Bean(value = {"dog", "dog1111", "dog2222"}, initMethod = "myWork", destroyMethod = "myDream")
    public Person person1(){
        System.out.println("���� Person");
        return new Person(11,"wang","dog_wang");
    }
}
