package com.haimin.li.spring.annotation.pojo;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class ImportSelectorImpl implements ImportSelector {
    //����ֵ�����ǵ����뵽�����е����ȫ����
    //AnnotationMetadata:��ǰ��ע@Importע����������ע����Ϣ
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //���� String[] null��ֵ���쳣
        return new String[]{"com.haimin.li.spring.annotation.pojo.Pink"};
    }
}
