package com.social.commission.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * @author Haimin Li
 * @description: TODO
 * @date 2019/8/6  15:45
 */
@ApiModel("AccountCommissionResultVO")
@Data
public class AccountCommissionResultVO implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty("未结算预估")
    private String finishAmountPre;
    @ApiModelProperty("已结算收益")
    private String noFinishAmountPre;
    @ApiModelProperty("已结算收益")
    private int intFlag;
}
