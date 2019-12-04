//package com.social.commerce.member.common;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.Map;
//import java.util.TreeMap;
//
///**
// * @author Haimin Li
// * @description: TODO
// * @date 2019/6/26  11:08
// */
//@Slf4j
//public class XLUtil {
//
//    public static String requsetToUrl(Map<String, String> map, String url, String appSecret) throws Exception {
//        map.put("currentTime", System.currentTimeMillis() + "");
//        String signStr = getSignString(map);
//        String sign = Md5Util.md5Lower(signStr + "&" + appSecret);
//        map.put("sign", sign);
//        String result = HttpUtil.httpPost(url, map);
//        return result;
//    }
//
//
//    public static String getSignString(Map<String, String> params){
//        Map<String, String> sortMap = new TreeMap<String, String>();
//        sortMap.putAll(params);
//        // 以k1=v1&k2=v2...方式拼接参数
//        StringBuilder builder = new StringBuilder();
//        for (Map.Entry<String, String> s : sortMap.entrySet()) {
//            String k = s.getKey();
//            String v = s.getValue();
//            if (StringUtils.isBlank(v)) {// 过滤空值
//                continue;
//            }
//            builder.append(k).append("=").append(v).append("&");
//        }
//        if (!sortMap.isEmpty()) {
//            builder.deleteCharAt(builder.length() - 1);
//        }
//        return builder.toString();
//    }
//}
