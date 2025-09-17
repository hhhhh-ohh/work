package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.enums.SellPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author wur
 * @className QueryTradeTodoRequest
 * @description TODO
 * @date 2022/4/27 15:00
 **/
@Schema
@Data
public class QueryTradeTodoRequest {

    @Schema(description = "代销平台标识")
    @NotNull
    private SellPlatformType sellPlatformType;
}