package com.haimin.li.spring.design.proxy;

public class StaticProxy {

    /**
     *   ��̬����ʵ��
     *
     */
    public static void main(String[] args) {
        //Ŀ�����
        UsersImpl target = new UsersImpl();

        //�������,��Ŀ����󴫸��������,���������ϵ
        UserDaoProxy proxy = new UserDaoProxy(target);

        proxy.save();//ִ�е��Ǵ���ķ���
    }

    /**
     * ��̬����Ҫʵ����ͬ�Ľӿ�
     */
    public static class UserDaoProxy implements IUsers{

        private UsersImpl target;

        public UserDaoProxy(UsersImpl target){
            this.target=target;
        }

        public int save() {
            System.out.println("��ʼ����...");
            target.save();//ִ��Ŀ�����ķ���
            System.out.println("�ύ����...");
            return 1;
        }
    }
}
