package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.marketing.bean.dto.StoreFreightDTO;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @description 购物车页-自动选券-优惠券明细入参
 * @author malianfeng
 * @date 2022/5/28 17:55
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponCodeAutoSelectForCartRequest extends CouponCodeListForUseByCustomerIdRequest {

    private static final long serialVersionUID = -5282885518390622918L;

    /**
     * 需要计算的券列表
     */
    @Schema(description = "需要计算的券列表")
    private List<CouponCodeVO> skuCouponCodeVos;

    /**
     * 已领取的券ID列表
     */
    @Schema(description = "已领取的券ID列表")
    private Set<String> hasFetchedCouponIds;

    /**
     * 用户自选券 couponCodeIds，此列表有值时，仅走部分自动选券逻辑
     * 如：用户更改了满系券的自动选券方案，但是没有更改运费券的方案，仅走部分自动选券逻辑
     */
    @Schema(description = "用户自选券 couponCodeIds，此列表有值时，不走完整的自动选券逻辑")
    private List<String> customCouponCodeIds;

    /**
     * 店铺运费列表
     */
    @NotEmpty
    @Valid
    @Schema(description = "店铺运费列表")
    private List<StoreFreightDTO> storeFreights;
}
