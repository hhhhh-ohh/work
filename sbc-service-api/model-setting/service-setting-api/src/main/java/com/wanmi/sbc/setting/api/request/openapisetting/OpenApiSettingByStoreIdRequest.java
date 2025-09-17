package com.wanmi.sbc.setting.api.request.openapisetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @description 开放平台api设置列表查询请求参
 * @author wur
 * @date 2021/4/25 11:07:15
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingByStoreIdRequest extends BaseRequest {
    private static final long serialVersionUID = 2647676343061160962L;

    /** 店铺id */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

}
