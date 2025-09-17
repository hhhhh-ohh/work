package com.wanmi.sbc.setting.api.request.pagemanage;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema
public class PageInfoExtendByCouponInfoRequest extends SettingBaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "优惠券活动ID")
    @NotBlank
    private String activityId;

    @Schema(description = "券ID")
    @NotBlank
    private String couponId;

}
