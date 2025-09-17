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
public class CouponActivityPauseByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 2823715642766459947L;

    /**
     * 活动id
     */
    @Schema(description = "优惠券活动id")
    @NotBlank
    private String id;
}
