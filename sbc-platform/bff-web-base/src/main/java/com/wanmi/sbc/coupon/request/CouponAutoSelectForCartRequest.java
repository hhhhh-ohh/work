package com.wanmi.sbc.coupon.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.coupon.dto.CouponSelectGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.StoreFreightDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

import java.util.List;

/**
 * @description 购物车页-自动选券-优惠券明细入参
 * @author malianfeng
 * @date 2022/5/28 17:55
 */
@Schema
@Data
public class CouponAutoSelectForCartRequest extends BaseRequest {

    private static final long serialVersionUID = 7881538554883736838L;

    /**
     * 商品信息
     */
    @Schema(description = "商品信息")
    @NotEmpty
    @Valid
    private List<CouponSelectGoodsInfoDTO> goodsInfos;

    /**
     * 加价购商品信息
     */
    @Schema(description = "加价购商品信息")
    @Valid
    private List<CouponSelectGoodsInfoDTO> preferentialGoodsInfos;

    /**
     * 优惠券ID列表
     */
    @Schema(description = "优惠券ID列表")
    private List<String> couponIds;

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
