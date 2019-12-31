package com.haimin.li.spring.annotation.tool;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class MyFilter implements TypeFilter {
    /**
     * metadataReader����ȡ���ĵ�ǰ����ɨ��������Ϣ
     * metadataReaderFactory:���Ի�ȡ�������κ�����Ϣ��
     */
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //��ȡ��ǰ��ע�����Ϣ
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //��ȡ��ǰ����ɨ����������Ϣ
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //��ȡ��ǰ����Դ�����·����
        Resource resource = metadataReader.getResource();
        String className = classMetadata.getClassName();
        if(className.contains("ao")){
            return true;
        }
        return false;
    }
}
