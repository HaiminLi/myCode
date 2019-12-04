package com.social.commission.api.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("CommissionOrderManagerPO")
public class CommissionOrderManagerPO implements Serializable {
    private static final long serialVersionUID = -7862149362467651906L;

    @ApiModelProperty("佣金流水id")
    private Long commissionId;
    @ApiModelProperty("淘宝客订单编号")
    private String parentOrderCode;
    @ApiModelProperty("一级订单号")
    private String firstOrderCode;
    @ApiModelProperty("订单号")
    private String orderCode;
    @ApiModelProperty("唯一标识（获取佣金：1；扣减佣金：首子单号*子单数量）")
    private String soleFlag;
    @ApiModelProperty("佣金方向（1：获取； 2：扣减）")
    private String commissionDirection;
    @ApiModelProperty("佣金批次号")
    private String batchCode;
    @ApiModelProperty("淘宝客会员商品付费金额")
    private BigDecimal payPrice;
    @ApiModelProperty("订单渠道(星链,淘宝客,黄豆侠)")
    private String orderType;
    @ApiModelProperty("订单日期")
    private Date orderTime;
    @ApiModelProperty("2C会员id")
    private Long userMemberId;
    @ApiModelProperty("商户id")
    private Long tenantId;
    @ApiModelProperty("会员等级")
    private String memberLevel;
    @ApiModelProperty("佣金总额")
    private BigDecimal commissionTotalPrice;
    @ApiModelProperty("实际佣金总额")
    private BigDecimal realityCommissionTotalPrice;
    @ApiModelProperty("预估分佣时间")
    private Date commissionTime;
    @ApiModelProperty("实际分佣时间")
    private Date realityCommissionTime;
    @ApiModelProperty("有效标识（00：无效；01：有效）")
    private String validFlag;
    @ApiModelProperty("分佣状态(01:未分佣, 02:已分佣)")
    private String commissionStatus;
    @ApiModelProperty("向钱包推送流水状态（0：失败；1：成功；其他：未推送）")
    private String pushWalletStatus;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("更新人")
    private String updateUser;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("创建人")
    private String createUser;
    @ApiModelProperty("删除标识（1：有效；0：删除）")
    private String delFlag;
}