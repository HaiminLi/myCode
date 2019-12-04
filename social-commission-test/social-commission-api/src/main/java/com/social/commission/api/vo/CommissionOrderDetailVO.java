package com.social.commission.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Haimin Li
 * @description: TODO
 * @date 2019/8/6  17:28
 */
@ApiModel("CommissionOrderDetailDTO")
@Data
public class CommissionOrderDetailVO implements Serializable {
    private static final long serialVersionUID = -1;

    @ApiModelProperty("订单编号")
    private String orderCode;
    @ApiModelProperty("显示用的订单编号")
    private String showOrderCode;
    @ApiModelProperty("订单时间")
    private String orderTime;
    @ApiModelProperty("订单渠道(01：星链,02：淘宝客:03：黄豆侠)")
    private String orderType;
    @ApiModelProperty("用户支付金额")
    private String payPrice;
    @ApiModelProperty("商品图片")
    private String productImg;
    @ApiModelProperty("分佣状态(01:未分佣, 02:已分佣， 03：已失效)")
    private String commissionStatus;
    @ApiModelProperty("结算时间")
    private String realityCommissionTime;
    @ApiModelProperty("佣金金额")
    private String amount;
}
