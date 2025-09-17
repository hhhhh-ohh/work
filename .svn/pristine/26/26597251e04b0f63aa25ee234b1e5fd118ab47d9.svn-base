package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.dto.CouponCodeAutoSelectDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description 自动选券出参
 * @author malianfeng
 * @date 2022/5/27 16:32
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCodeAutoSelectResponse extends BasicResponse {

    private static final long serialVersionUID = 3709822540083783038L;

    @Schema(description = "选券（满减、满折券）列表")
    private List<CouponCodeAutoSelectDTO> selectCoupons;

    @Schema(description = "选券（运费券）列表")
    private List<CouponCodeAutoSelectDTO> selectFreightCoupons;

    @Schema(description = "总实际抵扣")
    private BigDecimal totalActualDiscount = BigDecimal.ZERO;

    @Schema(description = "（满减、满折券）实际抵扣总和")
    private BigDecimal sumActualDiscount = BigDecimal.ZERO;

    @Schema(description = "（运费券）实际抵扣总和")
    private BigDecimal sumFreightActualDiscount = BigDecimal.ZERO;
}
