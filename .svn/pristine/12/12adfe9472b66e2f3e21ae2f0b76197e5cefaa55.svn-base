package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.order.bean.vo.DiscountsVO;
import com.wanmi.sbc.order.bean.vo.OrderTagVO;
import com.wanmi.sbc.order.bean.vo.TradeConfirmItemVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午5:58 2018/9/28
 * @Description: 订单确认返回结构
 */
@Schema
@Data
public class TradeConfirmResponse {

    /**
     * 是否为分销员
     */
    @Schema(description = "是否为分销员")
    private DefaultFlag isDistributor;

    /**
     * 按商家拆分后的订单项
     */
    @Schema(description = "按商家拆分后的订单项")
    private List<TradeConfirmItemVO> tradeConfirmItems;

    /**
     * 订单总额
     */
    @Schema(description = "订单总额")
    private BigDecimal totalPrice = BigDecimal.ZERO;

    /**
     * 商品总额
     */
    @Schema(description = "商品总额")
    private BigDecimal goodsTotalPrice = BigDecimal.ZERO;
    /**
     * 商品税费
     */
    @Schema(description = "商品综合税费")
    private BigDecimal totalTaxPrice = new BigDecimal("0.00");
    /**
     * 优惠总额
     */
    @Schema(description = "优惠总额")
    private BigDecimal discountsTotalPrice = BigDecimal.ZERO;

    /**
     * 未使用的优惠券
     */
    @Schema(description = "未使用的优惠券")
    private List<CouponCodeVO> couponCodes;

    /**
     * 可用优惠券数量
     */
    @Schema(description = "可用优惠券数量（满减、满折）")
    private Integer couponAvailableCount;

    /**
     * 可用运费券数量
     */
    @Schema(description = "可用运费券数量")
    private Integer freightCouponAvailableCount;

    /**
     * 小店名称
     */
    @Schema(description = "小店名称")
    private String shopName;

    /**
     * 邀请人名称
     */
    @Schema(description = "邀请人名称")
    private String inviteeName;

    /**
     * 返利总额
     */
    @Schema(description = "返利总额")
    private BigDecimal totalCommission;

    /**
     * 是否开店礼包
     */
    @Schema(description = "是否开店礼包")
    private DefaultFlag storeBagsFlag = DefaultFlag.NO;

    /**
     * 是否组合套装
     */
    private Boolean suitMarketingFlag;

    /**
     * 是否开团购买(true:开团 false:参团 null:非拼团购买)
     */
    @Schema(description = "是否开团购买")
    private Boolean openGroupon;

    /**
     * 拼团活动是否包邮
     */
    @Schema(description = "拼团活动是否包邮")
    private boolean grouponFreeDelivery;

    /**
     * 秒杀活动是否包邮
     */
    @Schema(description = "秒杀活动是否包邮")
    private Boolean flashFreeDelivery;

    /**
     * 购买积分，被用于普通订单的积分+金额混合商品
     */
    @Schema(description = "购买积分")
    private Long totalBuyPoint = 0L;

    /**
     * 是否购物车购买
     */
    @Schema(description = "是否购物车购买")
    private Boolean purchaseBuy;

    /**
     * 订单标记
     */
    @Schema(description = "订单标记")
    private OrderTagVO orderTagVO;

    @Schema(description = "是否砍价商品标识")
    private Boolean bargainJoinGoodsInfo;

    @Schema(description = "砍价标识")
    private Boolean bargain;

    @Schema(description = "砍价Id")
    public Long bargainId;

    @Schema(description = "可用礼品卡数量")
    public Long giftCardNum;

    @Schema(description = "提货卡名称")
    private String pickUpCardName;
//    @Schema(description = "校服购买总数")
//    private Integer schoolNum;

    public BigDecimal getTotalPrice() {
        return tradeConfirmItems.stream().map(i -> i.getTradePrice().getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getGoodsTotalPrice() {
        return tradeConfirmItems.stream().map(i -> i.getTradePrice().getGoodsPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getDiscountsTotalPrice() {
        return tradeConfirmItems.stream().flatMap(i -> i.getDiscountsPrice().stream()).map(DiscountsVO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public BigDecimal getTotalTaxPrice() {
        return tradeConfirmItems.stream().map(i -> i.getTradePrice().getTotalTax())
                .reduce(new BigDecimal("0.00"), BigDecimal::add);
    }
}