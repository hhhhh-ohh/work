package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponActivityConfigSaveRequest extends BaseRequest {

    /**
     * 优惠券id
     */
    @Schema(description = "优惠券id")
    @NotBlank
    private String couponId;


    /**
     * 优惠券总张数
     */
    @Schema(description = "优惠券总张数")
    @NotNull
    private Long totalCount;
}
