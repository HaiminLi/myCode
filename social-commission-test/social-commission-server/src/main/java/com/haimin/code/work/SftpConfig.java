//package com.haimin.code.work;
//
//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.Session;
//import com.jcraft.jsch.SftpException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//import java.util.Vector;
//
///**
// * .
// * <p>项目名称: social-order </p>
// * <p>文件名称: null.java </p>
// * <p>描述: [类型描述] </p>
// * <p>创建时间: 2019/12/18 </p>
// * <p>公司信息: </p>
// *
// * @author <a href="mail to: yanghua@ihdou.com" rel="nofollow">huayang</a>
// * @version v1.0.0
// * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
// */
//@Slf4j
//@Configuration
//public class SftpConfig {
//
//  /**
//   * SFTP 登录用户名
//   */
//  @Value("${sftp.user}")
//  private String user;
//  /**
//   * SFTP 登录密码
//   */
//  @Value("${sftp.password}")
//  private String password;
//  /**
//   * 私钥
//   */
//  @Value("${sftp.privateKey}")
//  private String privateKey;
//  /**
//   * SFTP 服务器地址IP地址
//   */
//  @Value("${sftp.host}")
//  private String host;
//  /**
//   * SFTP 端口
//   */
//  @Value("${sftp.port}")
//  private String port;
//
//  /**
//   * 服务器的基础路径
//   */
//  @Value("${sftp.file.base.path}")
//  private String basePath;
//
//  private Session session;
//
//  private ChannelSftp channelSftp;
//
//  @Bean
//  public ChannelSftp sftp() {
//    try {
//      JSch jsch = new JSch();
//      if (!StringUtils.isEmpty(privateKey)) {
//        // 设置私钥
//        jsch.addIdentity(privateKey);
//      }
//      session = jsch.getSession(user, host, StringUtils.isEmpty(port) ? 22 : Integer.valueOf(port));
//      if (!StringUtils.isEmpty(password)) {
//        session.setPassword(password);
//      }
//      Properties config = new Properties();
//      config.put("StrictHostKeyChecking", "no");
//
//      session.setConfig(config);
//      session.connect();
//
//      Channel channel = session.openChannel("sftp");
//      channel.connect();
//
//      channelSftp = (ChannelSftp) channel;
//
//    } catch (JSchException e) {
//      e.printStackTrace();
//    }
//    return channelSftp;
//  }
//
//
//  /**
//   * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
//   *
//   * @param directory 上传到该目录
//   * @param sftpFileName sftp端文件名
//   */
//  public void upload(String directory, String sftpFileName, InputStream input)
//      throws SftpException {
//    try {
//      channelSftp.cd(basePath);
//      channelSftp.cd(directory);
//    } catch (SftpException e) {
//      //目录不存在，则创建文件夹
//      String[] dirs = directory.split("/");
//      String tempPath = basePath;
//      for (String dir : dirs) {
//        if (null == dir || "".equals(dir)) {
//          continue;
//        }
//        tempPath += "/" + dir;
//        try {
//          channelSftp.cd(tempPath);
//        } catch (SftpException ex) {
//          channelSftp.mkdir(tempPath);
//          channelSftp.cd(tempPath);
//        }
//      }
//    }
//    //上传文件
//    channelSftp.put(input, sftpFileName);
//  }
//
//
//  /**
//   * 下载文件。
//   *
//   * @param directory 下载目录
//   * @param downloadFile 下载的文件
//   * @param saveFile 存在本地的路径
//   */
//  public void download(String directory, String downloadFile, String saveFile)
//      throws SftpException, FileNotFoundException {
//    if (directory != null && !"".equals(directory)) {
//      channelSftp.cd(basePath+directory);
//    }
//    File file = new File(saveFile);
//    channelSftp.get(downloadFile, new FileOutputStream(file));
//  }
//
//  /**
//   * 下载文件
//   *
//   * @param directory 下载目录
//   * @param downloadFile 下载的文件名
//   * @return 字节数组
//   */
//  public byte[] download(String directory, String downloadFile) throws SftpException, IOException {
//    if (directory != null && !"".equals(directory)) {
//      channelSftp.cd(basePath+directory);
//    }
//    InputStream is = channelSftp.get(downloadFile);
//
//    byte[] fileData = IOUtils.toByteArray(is);
//
//    return fileData;
//  }
//
//
//  /**
//   * 删除文件
//   *
//   * @param directory 要删除文件所在目录
//   * @param deleteFile 要删除的文件
//   */
//  public void delete(String directory, String deleteFile) throws SftpException {
//    channelSftp.cd(basePath+directory);
//    channelSftp.rm(deleteFile);
//  }
//
//
//  /**
//   * 列出目录下的文件
//   *
//   * @param directory 要列出的目录
//   */
//  public Vector<?> listFiles(String directory) throws SftpException {
//    return channelSftp.ls(basePath+directory);
//  }
//
//}
