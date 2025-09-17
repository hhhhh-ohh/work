package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据id查询优惠券视图详情的请求结构
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponInfoDetailByIdRequest extends BaseRequest {

    private static final long serialVersionUID = -4321352902686829315L;

    /**
     * 优惠券id
     */
    @Schema(description = "优惠券Id")
    @NotBlank
    private String couponId;

    /**
     * 店铺id
     */
    private Long storeId;
}
