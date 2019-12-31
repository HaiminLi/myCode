package com.haimin.li.spring.annotation.config;

import com.haimin.li.spring.annotation.pojo.Color;
import com.haimin.li.spring.annotation.pojo.ImportBeanDefinitionRegistrarImpl;
import com.haimin.li.spring.annotation.pojo.ImportSelectorImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({Color.class, Color.class, ImportSelectorImpl.class, ImportBeanDefinitionRegistrarImpl.class})//
public class importAnnotationConfig {
    /*
     * import 多个相同的组件，只注册一个
     *
     */
}
