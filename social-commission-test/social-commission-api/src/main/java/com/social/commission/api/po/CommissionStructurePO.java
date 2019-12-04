package com.social.commission.api.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("CommissionStructurePO")
public class CommissionStructurePO implements Serializable {
    private static final long serialVersionUID = -1579302612032409307L;

    @ApiModelProperty("分佣结构id")
    private Long structureId;
    @ApiModelProperty("订单编号")
    private String orderCode;
    @ApiModelProperty("分佣结构用户id")
    private Long userId;
    @ApiModelProperty("用户等级")
    private String memberLevel;
    @ApiModelProperty("分佣标识（0：未分佣；1分佣；2基准分佣用户）")
    private String commissionFlag;
    @ApiModelProperty("显示序号")
    private Integer displaySeq;
    @ApiModelProperty("商户id")
    private Long tenantId;
}