package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author EDZ
 * @className MagicCouponInfoRequest
 * @description h5/app优惠券魔方页面，优惠券数据状态查询
 * @date 2021/6/2 17:17
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MagicCouponInfoRequest extends BaseRequest {

    @Schema(description = "优惠券活动ID")
    @NotBlank
    private String activityId;

    @Schema(description = "优惠券活动ID")
    @NotBlank
    private String couponId;

    @Schema(description = "优惠券CodeID")
    private String couponCodeId;

    @Schema(description = "门店id")
    private Long storeId;

}

