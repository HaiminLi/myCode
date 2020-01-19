package com.haimin.li.spring.design.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy {

    public static void main(String[] args) {
//Ŀ�����
        Users target = new Users();

        //�������
        Users proxy = (Users)new ProxyFactory(target).getProxyInstance();

        //ִ�д������ķ���
          proxy.save("����");
    }

    public static class ProxyFactory implements MethodInterceptor {
        //ά��Ŀ�����
        private Object target;

        public ProxyFactory(Object target) {
            this.target = target;
        }

        //��Ŀ����󴴽�һ���������
        public Object getProxyInstance(){
            //1.������
            Enhancer en = new Enhancer();
            //2.���ø���
            en.setSuperclass(target.getClass());
            //3.���ûص�����
            en.setCallback(this);
            //4.��������(�������)
            return en.create();

        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("��ʼ����...");

            //ִ��Ŀ�����ķ���
            Object returnValue = method.invoke(target, objects);

            System.out.println("�ύ����...");

            return returnValue;
        }
    }
}
