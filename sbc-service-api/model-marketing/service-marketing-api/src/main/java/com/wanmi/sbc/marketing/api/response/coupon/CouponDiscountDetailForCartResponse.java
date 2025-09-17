package com.wanmi.sbc.marketing.api.response.coupon;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description 购物车页-自动选券-优惠券明细出参
 * @author malianfeng
 * @date 2022/5/28 17:55
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDiscountDetailForCartResponse extends CouponCodeAutoSelectResponse {

    private static final long serialVersionUID = 3709822540083783038L;

    @Schema(description = "是否存在待领的优惠券，只针对SKU，不考虑是否适用")
    private Boolean hasWaitFetchedFlag = Boolean.FALSE;

    @Schema(description = "已领券（满减、满折券）抵扣")
    private BigDecimal fetchedDiscount = BigDecimal.ZERO;

    @Schema(description = "待领券（满减、满折券）抵扣")
    private BigDecimal waitFetchedDiscount = BigDecimal.ZERO;

    @Schema(description = "已领券（运费券）抵扣")
    private BigDecimal fetchedFreightDiscount = BigDecimal.ZERO;

    @Schema(description = "待领券（满减、满折券）抵扣")
    private BigDecimal waitFetchedFreightDiscount = BigDecimal.ZERO;
}
