package com.social.commission.server.common;

import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

/**
 * @Author Haimin Li
 * @Description //TODO
 * @Date 2019/6/26 11:26
 * @Param 
 * @return 
 **/
public class Md5Util {

  /**
   * 32位小写md5加密
   * @param str
   * @return
   */
  public static String md5Lower(String str){
    String result = "";
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
      md5.update((str).getBytes("UTF-8"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    byte b[] = md5.digest();
    int i;
    StringBuffer buf = new StringBuffer("");

    for(int offset=0; offset<b.length; offset++){
      i = b[offset];
      if(i<0){
        i+=256;
      }
      if(i<16){
        buf.append("0");
      }
      buf.append(Integer.toHexString(i));
    }

    result = buf.toString();
    return result;
  }

  /**
   * 参数按字典排序
   */
  public static void sortByDictionary(List<String> list) {
    Collections.sort(list);
  }
}
