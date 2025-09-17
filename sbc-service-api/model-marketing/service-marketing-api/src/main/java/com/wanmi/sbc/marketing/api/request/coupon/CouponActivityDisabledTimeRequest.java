package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@NoArgsConstructor
public class CouponActivityDisabledTimeRequest extends BaseRequest {

    private static final long serialVersionUID = 4026920269915546425L;
    /**
     * 优惠券活动类型，0全场赠券，1指定赠券，2进店赠券，3注册赠券， 4权益赠券
     */
    @Schema(description = "优惠券活动类型")
    @NotNull
    private CouponActivityType couponActivityType;

    /**
     * 活动id
     */
    @Schema(description = "优惠券活动id")
    private String activityId;

    /**
     * 商户id
     */
    @Schema(description = "店铺id")
    private Long storeId;
}
