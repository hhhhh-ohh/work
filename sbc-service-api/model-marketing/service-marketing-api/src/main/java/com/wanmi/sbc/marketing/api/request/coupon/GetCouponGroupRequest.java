package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Schema
@Data
public class GetCouponGroupRequest extends BaseRequest {

    @Schema(description = "客户id")
    @NotNull
    private String customerId;

    @Schema(description = "优惠券活动类型")
    @NotNull
    private CouponActivityType type;

    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;
}
