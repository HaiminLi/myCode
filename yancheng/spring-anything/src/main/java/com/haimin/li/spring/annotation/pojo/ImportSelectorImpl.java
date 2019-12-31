package com.haimin.li.spring.annotation.pojo;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class ImportSelectorImpl implements ImportSelector {
    //返回值，就是到导入到容器中的组件全类名
    //AnnotationMetadata:当前标注@Import注解的类的所有注解信息
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //返回 String[] null数值会异常
        return new String[]{"com.haimin.li.spring.annotation.pojo.Pink"};
    }
}
