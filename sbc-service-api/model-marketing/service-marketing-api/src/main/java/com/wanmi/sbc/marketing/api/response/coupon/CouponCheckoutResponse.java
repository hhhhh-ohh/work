package com.wanmi.sbc.marketing.api.response.coupon;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.CheckGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * 根据客户和券码id查询不可用的平台券以及优惠券实际优惠总额的响应结构
 * @Author: gaomuwei
 * @Date: Created In 上午10:49 2018/9/29
 * @Description:
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponCheckoutResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 没有达到门槛的平台优惠券码id
     */
    @Schema(description = "没有达到门槛的平台优惠券码id")
    private Set<String> unreachedIds;

    /**
     * 其他没有达到门槛的平台优惠券码id
     */
    @Schema(description = "其他没有达到门槛的平台优惠券码",hidden = true)
    @JsonIgnore
    private Set<String> allUnreachedIds;

    /**
     * 计算完优惠券均摊价的商品总价
     */
    @Schema(description = "计算完优惠券均摊价的商品总价")
    private BigDecimal totalPrice;

    /**
     * 优惠券优惠总价
     */
    @Schema(description = "优惠券优惠总价（满减/满折券）")
    private BigDecimal couponTotalPrice;

    /**
     * 优惠券优惠总价
     */
    @Schema(description = "运费券优惠总价")
    private BigDecimal freightCouponTotalPrice;

    /**
     * 均摊完优惠券后的商品价格
     */
    @Schema(description = "均摊完优惠券后的商品价格")
    List<CheckGoodsInfoVO> checkGoodsInfos;

    /**
     * 均摊完优惠券后的加价购商品价格
     */
    @Schema(description = "均摊完优惠券后的加价购商品价格")
    List<CheckGoodsInfoVO> checkPreferentialSku;

    /**
     * 可用优惠券数量
     */
    @Schema(description = "可用优惠券数量（满减、满折）")
    private Long couponAvailableCount;

    /**
     * 可用运费券数量
     */
    @Schema(description = "可用运费券数量")
    private Long freightCouponAvailableCount;

    public CouponCheckoutResponse(Set<String> unreachedIds, Set<String> allUnreachedIds,
                                  BigDecimal totalPrice, BigDecimal couponTotalPrice, BigDecimal freightCouponTotalPrice,
                                  List<CheckGoodsInfoVO> checkGoodsInfos,
                                  Long couponAvailableCount, Long freightCouponAvailableCount) {
        this.unreachedIds = unreachedIds;
        this.allUnreachedIds = allUnreachedIds;
        this.totalPrice = totalPrice;
        this.couponTotalPrice = couponTotalPrice;
        this.freightCouponTotalPrice = freightCouponTotalPrice;
        this.checkGoodsInfos = checkGoodsInfos;
        this.couponAvailableCount = couponAvailableCount;
        this.freightCouponAvailableCount = freightCouponAvailableCount;
    }

    /**
     * 佣金
     */
    @Schema(description = "佣金")
    private BigDecimal commission;

}
