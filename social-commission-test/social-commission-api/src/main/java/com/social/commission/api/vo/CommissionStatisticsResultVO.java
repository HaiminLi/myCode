package com.social.commission.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Haimin Li
 * @description: TODO
 * @date 2019/7/17  20:02
 */
@ApiModel("CommissionStatisticsResultVO")
@Data
public class CommissionStatisticsResultVO implements Serializable {
    private static final long serialVersionUID = -7508332405344592139L;

    @ApiModelProperty("总计")
    private String total;
    @ApiModelProperty("导购预估")
    private String guideTotal;
    @ApiModelProperty("非导购预估")
    private String noGuideTotal;
}
