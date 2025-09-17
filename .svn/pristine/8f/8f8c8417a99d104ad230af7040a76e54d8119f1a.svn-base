package com.wanmi.sbc.setting.api.request.openapisetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @description 开放平台重置secret request
 * @author lvzhenwei
 * @date 2021/4/14 4:16 下午
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingResetSecretRequest extends BaseRequest {
    private static final long serialVersionUID = 2193080339806280995L;

    /** 主键 */
    @Schema(description = "主键")
    @NotNull
    private Long id;

    /** 店铺id */
    @Schema(description = "店铺id", hidden = true)
    private Long storeId;

    /** app secret */
    @Schema(description = "app secret", hidden = true)
    private String appSecret;
}
