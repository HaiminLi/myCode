package com.haimin.li.spring.annotation.config;


import com.haimin.li.spring.annotation.service.PersonServer;
import com.haimin.li.spring.annotation.tool.MyFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

//������==�����ļ�
@Configuration //����Spring����һ��������
@ComponentScan(value = "com.haimin.li.spring.annotation", useDefaultFilters = false,
        includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class}),
        @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,classes={PersonServer.class}),
        @ComponentScan.Filter(type=FilterType.CUSTOM,classes={MyFilter.class})
})
public class ComponentScanConfig {

    /*
     * @ComponentScan �÷�
     * value:ָ��Ҫɨ��İ�
     * excludeFilters = Filter[] ��ָ��ɨ���ʱ����ʲô�����ų���Щ���
     * includeFilters = Filter[] ��ָ��ɨ���ʱ��ֻ��Ҫ������Щ���  ��Ҫ���� useDefaultFilters = false����Ȼ��������,��Щ�����ǡ��򡯵Ĺ�ϵ
     * FilterType.ANNOTATION������ע��
     * FilterType.ASSIGNABLE_TYPE�����ո��������ͣ�
     * FilterType.ASPECTJ��ʹ��ASPECTJ���ʽ �������á�
     * FilterType.REGEX��ʹ������ָ�� �������á�
     * FilterType.CUSTOM��ʹ���Զ������
     */
}
