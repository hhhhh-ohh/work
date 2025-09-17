package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Schema
@Data
public class ModifyEsGoodsBoostRequest extends BaseRequest {

    private static final long serialVersionUID = -4670363194690270801L;
    /**
     * 配置内容
     */
    @Schema(description = "配置内容")
    @NotNull
    private String context;


}
