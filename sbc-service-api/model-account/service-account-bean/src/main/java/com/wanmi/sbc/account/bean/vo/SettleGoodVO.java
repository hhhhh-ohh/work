package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.account.bean.enums.ReturnStatus;
import com.wanmi.sbc.account.bean.enums.SettleCouponType;
import com.wanmi.sbc.account.bean.enums.SettleMarketingType;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hht on 2017/12/7.
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettleGoodVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 商品名称
     * trade - tradeItem - skuName
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * 商品sku编码
     * trade - tradeItem - skuNo
     */
    @Schema(description = "商品sku编码")
    private String skuNo;

    /**
     * 商品规格描述
     * trade - tradeItem - specDetails
     */
    @Schema(description = "商品规格描述")
    private String specDetails;

    /**
     * 商品价格
     * trade - tradeItem - price
     */
    @Schema(description = "商品价格")
    private BigDecimal goodsPrice;

    /**
     * 商品供货价
     * trade - tradeItem - price
     */
    @Schema(description = "商品供货价")
    private BigDecimal providerPrice;

    /**
     * 供货价
     *
     */
    @Schema(description = "供货价")
    private BigDecimal supplyPrice;

    /**
     * 三级分类名称
     * trade - tradeItem - cateName ?
     */
    @Schema(description = "三级分类名称")
    private String cateName;

    /**
     * 扣点
     */
    @Schema(description = "扣点")
    private BigDecimal cateRate;

    /**
     * 商品数量
     * trade - tradeItem - num
     */
    @Schema(description = "商品数量")
    private long num;

    /**
     * 实付金额
     */
    @Schema(description = "实付金额")
    private BigDecimal splitPayPrice;

    /**
     * 退货数量
     */
    @Schema(description = "退货数量")
    private Long returnNum;

    /**
     * 发起退单时的可退货数量
     */
    @Schema(description = "发起退单时的可退货数量")
    private Long canReturnNum;

    /**
     * 应退均摊金额
     */
    @Schema(description = "应退均摊金额")
    private BigDecimal shouldReturnPrice;

    /**
     * 货品skuId
     */
    @Schema(description = "货品skuId")
    private String skuId;

    /**
     * 退货状态
     */
    @Schema(description = "退货状态")
    private ReturnStatus returnStatus;

    /**
     * 营销相关的均摊价对象
     */
    @Schema(description = "营销相关的均摊价对象")
    private List<MarketingSettlement> marketingSettlements;

    /**
     * 优惠券商品结算信息
     */
    @Schema(description = "优惠券商品结算信息")
    private List<CouponSettlement> couponSettlements;

    /**
     * 积分抵现金额
     */
    @Schema(description = "积分抵现金额")
    private BigDecimal pointPrice;

    /**
     * 积分数量
     */
    @Schema(description = "积分数量")
    private Long points;

    /**
     * 佣金比例
     */
    @Schema(description = "佣金比例")
    private BigDecimal commissionRate;

    /**
     * 分销佣金
     */
    @Schema(description = "分销佣金")
    private BigDecimal commission;
    
    /**
     * 礼品卡金额
     */
    @Schema(description = "礼品卡抵扣金额")
    private BigDecimal giftCardPrice;

    /**
     * 礼品卡类型
     */
    @Schema(description = "礼品卡类型")
    private GiftCardType giftCardType;

    /**
     * 社区团购佣金
     */
    @Schema(description = "社区团购佣金")
    private BigDecimal communityCommission;

    /**
     * 社区团购佣金比例
     */
    @Schema(description = "社区团购佣金比例")
    private BigDecimal communityCommissionRate;

    /**
     * 营销优惠商品结算Bean
     */
    @Schema
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MarketingSettlement {

        /**
         * 营销类型
         */
        @Schema(description = "营销类型")
        private SettleMarketingType marketingType;

        /**
         * 除去营销优惠金额后的商品均摊价
         */
        @Schema(description = "除去营销优惠金额后的商品均摊价")
        private BigDecimal splitPrice;
    }

    /**
     * 优惠券优惠商品结算Bean
     */
    @Schema
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CouponSettlement implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 优惠券码id
         */
        @Schema(description = "优惠券码id")
        private String couponCodeId;

        /**
         * 优惠券码
         */
        @Schema(description = "优惠券码")
        private String couponCode;

        /**
         * 优惠券类型
         */
        @Schema(description = "优惠券类型")
        private SettleCouponType couponType;

        /**
         * 除去优惠金额后的商品均摊价
         */
        @Schema(description = "除去优惠金额后的商品均摊价")
        private BigDecimal splitPrice;

        /**
         * 优惠金额
         */
        @Schema(description = "优惠金额")
        private BigDecimal reducePrice;

    }
}
