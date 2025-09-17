package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @description  查询用户注册领取的优惠券
 * @author  wur
 * @date: 2021/12/3 10:42
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetRegisterCouponRequest extends BaseRequest {

    @Schema(description = "客户id")
    @NotNull
    private String customerId;

    @Schema(description = "优惠券活动类型")
    @NotNull
    private CouponActivityType couponActivityType;
}
