package com.wanmi.sbc.marketing.api.request.market;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingScopeByMarketingIdRequest extends BaseRequest {

    private static final long serialVersionUID = 2192532443872256505L;
    /**
     * 促销Id
     */
    @Schema(description = "营销id")
    @NotNull
    private Long marketingId;

}
