package com.social.commission.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Haimin Li
 * @description: TODO
 * @date 2019/8/6  19:58
 */
@Data
@ApiModel("FansContributionVO")
public class FansContributionVO implements Serializable {
    private static final long serialVersionUID = 5476454526790799105L;

    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("头像")
    private String headerImgUrl;
    @ApiModelProperty("注册手机号")
    private String userPhone;
    @ApiModelProperty("注册时间")
    private String registerTime;
    @ApiModelProperty("贡献度")
    private String contribution;
}
