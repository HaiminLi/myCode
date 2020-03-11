package com.haimin.li.jvm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NIO2 {

    public static void main(String[] args) throws IOException {
        serverBlocking();
        clientBlocking();
    }

    /*
     * һ��ʹ�� NIO �������ͨ�ŵ��������ģ�
     *
     * 1. ͨ����Channel������������
     *
     * 	   java.nio.channels.Channel �ӿڣ�
     * 			|--SelectableChannel
     * 				|--SocketChannel
     * 				|--ServerSocketChannel
     * 				|--DatagramChannel
     *
     * 				|--Pipe.SinkChannel
     * 				|--Pipe.SourceChannel
     *
     * 2. ��������Buffer�����������ݵĴ�ȡ
     *
     * 3. ѡ������Selector������ SelectableChannel �Ķ�·�����������ڼ�� SelectableChannel �� IO ״��
     *
     */
    public static void clientBlocking() throws IOException {
        //1. ��ȡͨ��
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("172.21.10.213", 9898));
//        sChannel.configureBlocking(false)
        FileChannel inChannel = FileChannel.open(Paths.get("E:", "/�ȸ�file", "/trip.pdf"), StandardOpenOption.READ);

        //2. ����ָ����С�Ļ�����
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //3. ��ȡ�����ļ��������͵������
        while(inChannel.read(buf) != -1){
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //4. �ر�ͨ��
        inChannel.close();
        sChannel.close();
    }

    public static void serverBlocking() throws IOException{
        //1. ��ȡͨ��
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        FileChannel outChannel = FileChannel.open(Paths.get("E:", "/�ȸ�file", "/new.pdf"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //2. ������
        ssChannel.bind(new InetSocketAddress(9898));

        //3. ��ȡ�ͻ������ӵ�ͨ��
        SocketChannel sChannel = ssChannel.accept();

        //4. ����ָ����С�Ļ�����
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //5. ���տͻ��˵����ݣ������浽����
        while(sChannel.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }

        //6. �ر�ͨ��
        sChannel.close();
        outChannel.close();
        ssChannel.close();

    }


    /*
		Files���÷�����
			Path copy(Path src, Path dest, CopyOption �� how) : �ļ��ĸ���
			Path createDirectory(Path path, FileAttribute<?> �� attr) : ����һ��Ŀ¼
			Path createFile(Path path, FileAttribute<?> �� arr) : ����һ���ļ�
			void delete(Path path) : ɾ��һ���ļ�
			Path move(Path src, Path dest, CopyOption��how) : �� src �ƶ��� dest λ��
			long size(Path path) : ���� path ָ���ļ��Ĵ�С
	 */

    /*
		Paths �ṩ�� get() ����������ȡ Path ����
			Path get(String first, String �� more) : ���ڽ�����ַ���������·����
		Path ���÷�����
			boolean endsWith(String path) : �ж��Ƿ��� path ·������
			boolean startsWith(String path) : �ж��Ƿ��� path ·����ʼ
			boolean isAbsolute() : �ж��Ƿ��Ǿ���·��
			Path getFileName() : ��������� Path ����������ļ���
			Path getName(int idx) : ���ص�ָ������λ�� idx ��·������
			int getNameCount() : ����Path ��Ŀ¼����Ԫ�ص�����
			Path getParent() ������Path�����������·���������� Path ����ָ�����ļ�·��
			Path getRoot() �����ص��� Path ����ĸ�·��
			Path resolve(Path p) :�����·������Ϊ����·��
			Path toAbsolutePath() : ��Ϊ����·�����ص��� Path ����
			String toString() �� ���ص��� Path ������ַ�����ʾ��ʽ
	 */
    public static void path(){
        Path path = Paths.get("E:/�ȸ�file/trip.pdf");

        System.out.println(path.getParent());
        System.out.println(path.getRoot());

//		Path newPath = path.resolve("e:/hello.txt");
//		System.out.println(newPath);

    }
}
