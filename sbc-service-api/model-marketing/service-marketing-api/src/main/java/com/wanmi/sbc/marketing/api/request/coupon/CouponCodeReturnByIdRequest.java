package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据id撤销优惠券使用请求结构
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCodeReturnByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 253886948264162993L;

    /**
     *  优惠券码id
     */
    @Schema(description = "优惠券码id")
    @NotBlank
    private String couponCodeId;

    /**
     *  用户id
     */
    @Schema(description = "用户id")
    @NotBlank
    private String customerId;

}
