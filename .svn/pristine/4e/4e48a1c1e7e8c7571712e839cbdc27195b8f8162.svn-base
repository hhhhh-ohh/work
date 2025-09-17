package com.wanmi.sbc.marketing.api.request.coupon;

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
public class CouponFetchRequest extends BaseRequest {

    private static final long serialVersionUID = -942007117002691667L;

    /**
     *  领券用户Id
     */
    @Schema(description = "领券用户Id")
    @NotBlank
    private String customerId;

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

    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     *  优惠券手动领取
     */
    @Schema(description = "优惠券手动领取")
    private Boolean fetchCouponFlag;

}
