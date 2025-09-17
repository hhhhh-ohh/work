package com.wanmi.sbc.marketing.api.request.preferential;

import com.wanmi.sbc.marketing.api.request.market.MarketingAddRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * @author edz
 * @className PreferentialAddRequest
 * @description 加价购活动请求类
 * @date 2022/11/17 14:13
 **/
@Data
@Schema
public class PreferentialRequest extends MarketingAddRequest {

    @Schema(description = "活动阶梯规则")
    @NotNull
    private List<PreferentialLevelRequest> preferentialLevelList;
}


