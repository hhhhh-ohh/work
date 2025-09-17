package com.wanmi.sbc.coupon.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 用户领取优惠券请求结构
 * @author daiyitian
 */
@Schema
@Data
public class CouponFetchBaseRequest extends BaseRequest {
    /**
     *  优惠券Id
     */
    @Schema(description = "优惠券Id")
    @NotBlank
    private String couponInfoId;

    /**
     *  优惠券活动Id
     */
    @Schema(description = "优惠券活动Id")
    @NotBlank
    private String couponActivityId;

}
