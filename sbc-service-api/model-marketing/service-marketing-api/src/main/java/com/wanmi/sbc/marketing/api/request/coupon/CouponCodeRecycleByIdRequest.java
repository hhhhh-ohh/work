package com.wanmi.sbc.marketing.api.request.coupon;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className CouponCodeRecycleByIdRequest
 * @description
 * @date 2022/5/23 7:18 PM
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponCodeRecycleByIdRequest implements Serializable {
    private static final long serialVersionUID = -5934055346675536166L;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    @NotBlank
    private String customerId;

    /**
     * 券码id
     */
    @Schema(description = "券码id")
    @NotBlank
    private String couponCodeId;
}
