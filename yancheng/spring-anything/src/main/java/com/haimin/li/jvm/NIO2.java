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
     * 一、使用 NIO 完成网络通信的三个核心：
     *
     * 1. 通道（Channel）：负责连接
     *
     * 	   java.nio.channels.Channel 接口：
     * 			|--SelectableChannel
     * 				|--SocketChannel
     * 				|--ServerSocketChannel
     * 				|--DatagramChannel
     *
     * 				|--Pipe.SinkChannel
     * 				|--Pipe.SourceChannel
     *
     * 2. 缓冲区（Buffer）：负责数据的存取
     *
     * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
     *
     */
    public static void clientBlocking() throws IOException {
        //1. 获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("172.21.10.213", 9898));
//        sChannel.configureBlocking(false)
        FileChannel inChannel = FileChannel.open(Paths.get("E:", "/谷歌file", "/trip.pdf"), StandardOpenOption.READ);

        //2. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //3. 读取本地文件，并发送到服务端
        while(inChannel.read(buf) != -1){
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //4. 关闭通道
        inChannel.close();
        sChannel.close();
    }

    public static void serverBlocking() throws IOException{
        //1. 获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        FileChannel outChannel = FileChannel.open(Paths.get("E:", "/谷歌file", "/new.pdf"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //2. 绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        //3. 获取客户端连接的通道
        SocketChannel sChannel = ssChannel.accept();

        //4. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //5. 接收客户端的数据，并保存到本地
        while(sChannel.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }

        //6. 关闭通道
        sChannel.close();
        outChannel.close();
        ssChannel.close();

    }


    /*
		Files常用方法：
			Path copy(Path src, Path dest, CopyOption … how) : 文件的复制
			Path createDirectory(Path path, FileAttribute<?> … attr) : 创建一个目录
			Path createFile(Path path, FileAttribute<?> … arr) : 创建一个文件
			void delete(Path path) : 删除一个文件
			Path move(Path src, Path dest, CopyOption…how) : 将 src 移动到 dest 位置
			long size(Path path) : 返回 path 指定文件的大小
	 */

    /*
		Paths 提供的 get() 方法用来获取 Path 对象：
			Path get(String first, String … more) : 用于将多个字符串串连成路径。
		Path 常用方法：
			boolean endsWith(String path) : 判断是否以 path 路径结束
			boolean startsWith(String path) : 判断是否以 path 路径开始
			boolean isAbsolute() : 判断是否是绝对路径
			Path getFileName() : 返回与调用 Path 对象关联的文件名
			Path getName(int idx) : 返回的指定索引位置 idx 的路径名称
			int getNameCount() : 返回Path 根目录后面元素的数量
			Path getParent() ：返回Path对象包含整个路径，不包含 Path 对象指定的文件路径
			Path getRoot() ：返回调用 Path 对象的根路径
			Path resolve(Path p) :将相对路径解析为绝对路径
			Path toAbsolutePath() : 作为绝对路径返回调用 Path 对象
			String toString() ： 返回调用 Path 对象的字符串表示形式
	 */
    public static void path(){
        Path path = Paths.get("E:/谷歌file/trip.pdf");

        System.out.println(path.getParent());
        System.out.println(path.getRoot());

//		Path newPath = path.resolve("e:/hello.txt");
//		System.out.println(newPath);

    }
}
