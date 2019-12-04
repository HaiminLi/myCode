package com.social.commission.server.common;

import java.util.HashMap;

/**
 * Created by haimin Li on 2018/8/28.
 */
public class StatusConstant {
  //删除标识-删除
  public final static String DELETE_0 = "0";
  //删除标识-未删除
  public final static String DELETE_1 = "1";

  //站内消息类型，扣减积分
  public final static String MESSAGE_TYPE_12 = "12";

  //积分商品状态-下架
  public final static String INTEGRAL_PRODUCT_STAUTS_00 = "00";
  //积分商品状态-在架
  public final static String INTEGRAL_PRODUCT_STAUTS_01 = "01";

  //积分商品类别-电子券
  public final static String PRODUCT_TYPE_01 = "01";
  //积分商品类别-实物商品
  public final static String PRODUCT_TYPE_02 = "02";

  //变更类型-兑换
  public final static String MODIFY_TYPE_01 = "01";
  //变更类型-上架
  public final static String MODIFY_TYPE_02 = "02";
  //变更类型-下架
  public final static String MODIFY_TYPE_03 = "03";
  //变更类型-删除
  public final static String MODIFY_TYPE_04 = "04";
  //变更类型-编辑
  public final static String MODIFY_TYPE_05 = "05";

  //应用网站-黄豆侠
  public final static String APPLY_SITE_01 = "01";

  //会员等级-注册会员
  public final static String MEMBER_LEVEL_01 = "01";
  //会员等级-普通会员
  public final static String MEMBER_LEVEL_02 = "02";
  //会员等级-铜牌会员
  public final static String MEMBER_LEVEL_03 = "03";
  //会员等级-银牌会员
  public final static String MEMBER_LEVEL_04 = "04";
  //会员等级-金牌会员
  public final static String MEMBER_LEVEL_05 = "05";
  //会员等级-钻石会员
  public final static String MEMBER_LEVEL_06 = "06";

  //规则类型-签到获得积分
  public final static String RULE_TYPE_01 = "01";
  //规则类型-积分失效规则
  public final static String RULE_TYPE_02 = "02";
  //规则类型-采购产品获得积分
  public final static String RULE_TYPE_03 = "03";
  //规则类型-连续签到获得积分
  public final static String RULE_TYPE_04 = "04";
  //规则类型-无理由拒收扣减积分
  public final static String RULE_TYPE_05 = "05";
  //规则类型-开通黄豆收款
  public final static String RULE_TYPE_06 = "06";
  //规则类型-完成首笔黄豆收款
  public final static String RULE_TYPE_07 = "07";
  //规则类型-当月完成首笔黄豆收款
  public final static String RULE_TYPE_08 = "08";
  //规则类型-当月完成 X 笔黄豆收款
  public final static String RULE_TYPE_09 = "09";
  //规则类型-每笔黄豆收款满足条件获得积分
  public final static String RULE_TYPE_10 = "10";
  //规则类型-当月完成首笔订单收货获得积分
  public final static String RULE_TYPE_11 = "11";
  //规则类型-手动调整积分
  public final static String RULE_TYPE_98 = "98";
  //规则类型-积分兑换
  public final static String RULE_TYPE_99 = "99";

  //积分所获类型-手动调整积分
  public final static String ACQUIRE_TYPE_02 = "02";
  //积分所获类型-积分兑换
  public final static String ACQUIRE_TYPE_03 = "03";

  //积分获得方向-获得
  public final static String ACQUIRE_DIRECTION_1 = "1";
  //积分获得方向-扣减
  public final static String ACQUIRE_DIRECTION_2 = "2";

  //积分明细说明-无理由退货，扣减积分
  public static String POINT_EXPLAIN_1(long value){
    return new StringBuilder("无理由拒收扣减").append(value).append("点积分").toString();
  }
  //积分明细说明-连续签到获得积分
  public static String POINT_EXPLAIN_2(long value){
    return new StringBuilder("连续签到获得").append(value).append("点积分").toString();
  }
  //积分明细说明-签到获得积分
  public static String POINT_EXPLAIN_3(long value){
    return new StringBuilder("签到获得").append(value).append("点积分").toString();
  }
  //积分明细说明-开通黄豆收款
  public static String POINT_EXPLAIN_4(long value){
    return new StringBuilder("开通黄豆收款获得").append(value).append("点积分").toString();
  }
  //积分明细说明-采购产品获得积分
  public static String POINT_EXPLAIN_5(String productName, long value){
    return new StringBuilder("采购").append(productName).append("产品获得").append(value).append("点积分").toString();
  }
  //积分明细说明-采购产品获得积分
  public static String POINT_EXPLAIN_6(long value){
    return new StringBuilder("使用黄豆收款获得").append(value).append("点积分").toString();
  }
  //积分明细说明-完成首笔黄豆收款获得积分
  public static String POINT_EXPLAIN_7(long value){
    return new StringBuilder("完成首笔收款获得").append(value).append("点积分").toString();
  }
  //积分明细说明-积分到期
  public static String POINT_EXPLAIN_8(long value){
    return new StringBuilder("扣减超过1年未使用的积分").append(value).append("点").toString();
  }
  //积分明细说明-当月首次使用黄豆收款获得积分
  public static String POINT_EXPLAIN_9(long value){
    return new StringBuilder("当月完成首笔收款获得").append(value).append("点积分").toString();
  }
  //积分明细说明-当月首次使用黄豆收款获得积分
  public static String POINT_EXPLAIN_10(long value){
    return new StringBuilder("完成首笔订单收货获得").append(value).append("点积分").toString();
  }

  //兑换状态-待发货
  public final static String EXCHANGE_STATUS_01= "01";
  //兑换状态-已发货
  public final static String EXCHANGE_STATUS_02= "02";
  //兑换状态-已收货
  public final static String EXCHANGE_STATUS_03= "03";

  //产品类目-功能机
  public final static String FLIGHTVALUE_96_2= "96-2";
  //产品类目-智能机
  public final static String FLIGHTVALUE_96_1= "96-1";

  //电子券状态-失效
  public final static String COUPON_STATUS_12= "12";
  //电子券状态-强制失效
  public final static String COUPON_STATUS_13= "13";
  //电子券状态-审核通过（生效）
  public final static String COUPON_STATUS_11= "11";
  //电子券状态-审核通过（未生效）
  public final static String COUPON_STATUS_15= "15";

  //库存-扣减库存
  public final static String INVENTORY_1= "1";
  //库存-归还库存
  public final static String INVENTORY_2= "2";

  //是否签到-已签到
  public final static String IF_SIGNIN_00= "00";
  //是否签到-未签到
  public final static String IF_SIGNIN_01= "01";

  //系统
  public final static String SYSTEM= "system";

  public static HashMap<String, String> AREA_CODE(){
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("146", "AH");
    hashMap.put("3587", "AM");
    hashMap.put("100001", "BJ");
    hashMap.put("100004", "CQ");
    hashMap.put("333", "FJ");
    hashMap.put("617", "GD");
    hashMap.put("512", "GS");
    hashMap.put("769", "GX");
    hashMap.put("896", "GZ");
    hashMap.put("1024", "HB");
    hashMap.put("1546", "WH");
    hashMap.put("1399", "HLJ");
    hashMap.put("998", "HK");
    hashMap.put("1221", "HN");
    hashMap.put("1673", "CS");
    hashMap.put("1814", "JL");
    hashMap.put("1885", "JS");
    hashMap.put("2044", "JX");
    hashMap.put("2158", "LN");
    hashMap.put("2282", "NMG");
    hashMap.put("3556", "NX");
    hashMap.put("2425", "QH");
    hashMap.put("2888", "SC");
    hashMap.put("2479", "SD");
    hashMap.put("100002", "SH");
    hashMap.put("2638", "SX");
    hashMap.put("2770", "XA");
    hashMap.put("100003", "TJ");
    hashMap.put("3588", "TW");
    hashMap.put("3586", "XG");
    hashMap.put("3181", "XJ");
    hashMap.put("3101", "XZ");
    hashMap.put("3302", "YN");
    hashMap.put("3447", "ZJ");
    return hashMap;
  }
}
