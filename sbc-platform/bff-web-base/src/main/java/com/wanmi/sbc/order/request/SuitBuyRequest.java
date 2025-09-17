package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Schema
@Data
public class SuitBuyRequest extends BaseRequest {

    /**
     * 活动主键
     */
    @Schema(description = "活动主键")
    @NotNull
    private Long marketingId;



}
