package com.social.commission.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Haimin Li
 * @description: TODO
 * @date 2019/7/9  10:18
 */
@Data
@ApiModel("ListResultVO")
public class ListResultVO implements Serializable {
    private static final long serialVersionUID = 9004210690897725128L;

    @ApiModelProperty("唯一的键值")
    private String tempId;
    @ApiModelProperty("userId")
    private Long userId;
    @ApiModelProperty("商户id")
    private Long tenantId;

    @ApiModelProperty("订单编号")
    private String orderCode;
    @ApiModelProperty("订单渠道(01：星链,02：淘宝客:03：黄豆侠)")
    private String orderType;
    @ApiModelProperty("佣金类型(01：商城商品分佣；02：导购商品分佣；03：会员商品分佣)")
    private String commissionType;
    @ApiModelProperty("订单日期")
    private String orderTime;
    @ApiModelProperty("会员手机号(实时)")
    private String memberMobile;
    @ApiModelProperty("会员等级（快照）")
    private String memberLevel;
    @ApiModelProperty("toC会员等级（快照）")
    private String cMemberLevel;
    @ApiModelProperty("预估佣金总额")
    private BigDecimal commissionTotalPrice;
    @ApiModelProperty("实际佣金总额")
    private BigDecimal realityCommissionTotalPrice;
    @ApiModelProperty("预估分佣时间")
    private String commissionTime;
    @ApiModelProperty("实际分佣时间")
    private String realityCommissionTime;
    @ApiModelProperty("分佣状态(01:未分佣, 02:已分佣)")
    private String commissionStatus;


    @ApiModelProperty("商品id")
    private String productId;
    @ApiModelProperty("商品名称")
    private String productName;
}
