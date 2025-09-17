package com.wanmi.sbc.marketing.api.request.coupon;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 满返支付精准发券
 * @author xufeng
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponCodeBatchSendFullReturnRequest implements Serializable {

    private static final long serialVersionUID = 4257934098662479018L;

    @NotBlank
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 优惠券id
     */
    @NotEmpty
    @Schema(description = "优惠券id")
    private List<String> couponIds;
}
