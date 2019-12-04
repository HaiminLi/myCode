package com.social.commission.api.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("CommissionProductDetailPO")
@Data
public class CommissionProductDetailPO implements Serializable {
    private static final long serialVersionUID = 1730611651965561172L;

    @ApiModelProperty("分佣明细id")
    private Long commissionDetailId;
    @ApiModelProperty("商户id")
    private Long tenantId;
    @ApiModelProperty("订单编号")
    private String orderCode;
    @ApiModelProperty("佣金方向（1：获取； 2：扣减）")
    private String commissionDirection;
    @ApiModelProperty("佣金批次号")
    private String batchCode;
    @ApiModelProperty("商品单号")
    private String productOrderCode;
    @ApiModelProperty("商品id")
    private String productId;
    @ApiModelProperty("商品名称")
    private String productName;
    @ApiModelProperty("商品图片")
    private String productImg;
    @ApiModelProperty("账单类型（1：预估账单；2结算账单）")
    private String accountType;
    @ApiModelProperty("佣金类型(01：商城商品分佣；02：导购商品分佣；03：会员商品分佣)")
    private String commissionType;
    @ApiModelProperty("分佣身份(01:粉丝佣金；02：会员级差； 03:平台佣金)")
    private String commissionStatus;
    @ApiModelProperty("分佣节点用户id")
    private Long commissionUserId;
    @ApiModelProperty("分佣用户节点排序")
    private Integer commissionUserOrder;
    @ApiModelProperty("会员等级")
    private String memberLevel;
    @ApiModelProperty("分佣比例")
    private Double commissionRate;
    @ApiModelProperty("商品欲估佣金总额")
    private BigDecimal commissionTotalPre;
    @ApiModelProperty("商品结算佣金总额")
    private BigDecimal commissionTotalReal;
    @ApiModelProperty("预估分佣金额")
    private BigDecimal commissionAmountPre;
    @ApiModelProperty("实际分佣金额")
    private BigDecimal commissionAmountReal;
    @ApiModelProperty("创建人")
    private String createUserId;
    @ApiModelProperty("产生日期")
    private Date createDate;
    @ApiModelProperty("更新人")
    private String updateUserId;
    @ApiModelProperty("更新日期")
    private Date updateDate;
    @ApiModelProperty("删除标识（1：有效；0：删除）")
    private String delFlag;
}