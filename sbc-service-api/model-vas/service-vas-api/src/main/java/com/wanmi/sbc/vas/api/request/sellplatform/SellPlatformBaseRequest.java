package com.wanmi.sbc.vas.api.request.sellplatform;

import com.wanmi.sbc.common.enums.SellPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * @description ThirdAddOrderRequest
 * @author wur
 * @date: 2022/4/20 9:39
 */
@Data
@Schema
public class SellPlatformBaseRequest implements Serializable {

    @NotNull
    @Schema(description = "代销平台标识")
    private SellPlatformType sellPlatformType = SellPlatformType.WECHAT_VIDEO;

}
