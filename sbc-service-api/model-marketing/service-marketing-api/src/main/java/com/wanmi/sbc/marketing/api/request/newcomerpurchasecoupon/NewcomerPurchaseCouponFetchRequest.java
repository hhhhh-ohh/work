package com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author xuyunpeng
 * @className NewcomerPurchaseCouponFetchRequest
 * @description
 * @date 2022/8/23 4:28 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseCouponFetchRequest extends BaseRequest {
    private static final long serialVersionUID = 2311418618190484407L;

    /**
     * 会员id
     */
    @NotBlank
    @Schema(description = "会员id")
    private String customerId;
}
