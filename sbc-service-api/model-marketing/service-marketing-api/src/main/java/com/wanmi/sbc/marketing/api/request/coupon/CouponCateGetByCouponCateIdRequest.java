package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponCateGetByCouponCateIdRequest extends BaseRequest {

    private static final long serialVersionUID = -634249564601290406L;

    /**
     * 优惠券分类id
     */
    @Schema(description = "优惠券分类Id")
    @NotBlank
    private String couponCateId;
}
