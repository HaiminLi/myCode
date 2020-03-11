package com.haimin.li.jvm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        send();
    }
    public void client() throws IOException {
        //1. ��ȡͨ��
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        //2. �л�������ģʽ
        sChannel.configureBlocking(false);

        //3. ����ָ����С�Ļ�����
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //4. �������ݸ������
        Scanner scan = new Scanner(System.in);

        while(scan.hasNext()){
            String str = scan.next();
            buf.put((new Date().toString() + "\n" + str).getBytes());
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //5. �ر�ͨ��
        sChannel.close();
    }

    public static void send() throws IOException{
        DatagramChannel dc = DatagramChannel.open();

        dc.configureBlocking(false);

        ByteBuffer buf = ByteBuffer.allocate(1024);

        Scanner scan = new Scanner(System.in);

        while(scan.hasNext()){
            String str = scan.next();
            buf.put((new Date().toString() + ":\n" + str).getBytes());
            buf.flip();
            dc.send(buf, new InetSocketAddress("127.0.0.1", 9898));
            buf.clear();
        }

        dc.close();
    }

}
