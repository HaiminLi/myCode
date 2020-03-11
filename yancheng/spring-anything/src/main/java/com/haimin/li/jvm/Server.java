package com.haimin.li.jvm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Server {
    public static void main(String[] args) throws IOException {
        receive();
        Collections.shuffle(new ArrayList<>());
    }

    public void server() throws IOException {
        //1. ��ȡͨ��
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        //2. �л�������ģʽ
        ssChannel.configureBlocking(false);

        //3. ������
        ssChannel.bind(new InetSocketAddress(9898));

        //4. ��ȡѡ����
        Selector selector = Selector.open();

        //5. ��ͨ��ע�ᵽѡ������, ����ָ�������������¼���
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        //6. ��ѯʽ�Ļ�ȡѡ�������Ѿ���׼�����������¼�
        while(selector.select() > 0){

            //7. ��ȡ��ǰѡ����������ע��ġ�ѡ���(�Ѿ����ļ����¼�)��
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while(it.hasNext()){
                //8. ��ȡ׼���������������¼�
                SelectionKey sk = it.next();

                //9. �жϾ�����ʲô�¼�׼������
                if(sk.isAcceptable()){
                    //10. �������վ���������ȡ�ͻ�������
                    SocketChannel sChannel = ssChannel.accept();

                    //11. �л�������ģʽ
                    sChannel.configureBlocking(false);

                    //12. ����ͨ��ע�ᵽѡ������
                    sChannel.register(selector, SelectionKey.OP_READ);
                }else if(sk.isReadable()){
                    //13. ��ȡ��ǰѡ�����ϡ���������״̬��ͨ��
                    SocketChannel sChannel = (SocketChannel) sk.channel();

                    //14. ��ȡ����
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    int len = 0;
                    while((len = sChannel.read(buf)) > 0 ){
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();
                    }
                }

                //15. ȡ��ѡ��� SelectionKey
                it.remove();
            }
        }
    }

    public static void receive() throws IOException{
        DatagramChannel dc = DatagramChannel.open();

        dc.configureBlocking(false);

        dc.bind(new InetSocketAddress(9898));

        Selector selector = Selector.open();

        dc.register(selector, SelectionKey.OP_READ);

        while(selector.select() > 0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while(it.hasNext()){
                SelectionKey sk = it.next();

                if(sk.isReadable()){
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    dc.receive(buf);
                    buf.flip();
                    System.out.println(new String(buf.array(), 0, buf.limit()));
                    buf.clear();
                }
            }

            it.remove();
        }
    }
}
