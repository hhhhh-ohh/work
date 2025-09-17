package com.wanmi.sbc.marketing.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午6:00 2019/3/13
 * @Description:
 */
@Schema
@Data
public class DistributionOpenFlagSaveRequest extends BaseRequest {

    /**
     * 是否开启社交分销 0：关闭，1：开启
     */
    @Schema(description = "是否开启社交分销")
    @NotNull
    private DefaultFlag openFlag;

}
