package com.haimin.li.spring.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MoveProxy {
    public static void main(String[] args) {
// Ŀ�����
        IUsers target = new UsersImpl();

        System.out.println(target.getClass());

        // ��Ŀ����󣬴����������
        IUsers proxy = (IUsers) new ProxyFactory(target).getProxyInstance();
        // class $Proxy0   �ڴ��ж�̬���ɵĴ������
        System.out.println(proxy.getClass());

        // ִ�з���   ���������
        proxy.save();
    }

    /**
     * ������̬�������
     * ��̬������Ҫʵ�ֽӿ�,������Ҫָ���ӿ�����
     */
    public static class ProxyFactory{
        //ά��һ��Ŀ�����
        private Object target;
        public ProxyFactory(Object target){
            this.target=target;
        }

        //��Ŀ��������ɴ������
        public Object getProxyInstance(){
            return Proxy.newProxyInstance(
                    target.getClass().getClassLoader(), //ָ����ǰĿ�����ʹ���������,��ȡ�������ķ����ǹ̶���
                    target.getClass().getInterfaces(),  //Ŀ�����ʵ�ֵĽӿڵ�����,ʹ�÷��ͷ�ʽȷ������
                    new InvocationHandler() {  //�¼�����,ִ��Ŀ�����ķ���ʱ,�ᴥ���¼��������ķ���,��ѵ�ǰִ��Ŀ�����ķ�����Ϊ��������
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("��ʼ����2");
                            //���÷���ִ��Ŀ����󷽷�
                            Object returnValue = method.invoke(target, args);

                            System.out.println("�ύ����2");
                            return returnValue;
                        }
                    }
            );
        }
    }
}
