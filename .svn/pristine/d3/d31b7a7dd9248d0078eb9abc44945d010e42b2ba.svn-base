package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 结算明细前台和导出公用展示实体
 */
@Schema
@Data
public class SettlementDetailGoodsViewVO extends BasicResponse {

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * 商品sku编码
     */
    @Schema(description = "商品sku编码")
    private String skuNo;

    /**
     * 商品规格值
     */
    @Schema(description = "商品规格值")
    private String specDetails;

    /**
     * 商品价格
     */
    @Schema(description = "商品价格")
    private BigDecimal goodsPrice;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String cateName;

    /**
     * 扣点
     */
    @Schema(description = "扣点")
    private String cateRate;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Long num;

    /**
     * 是否特价
     */
    @Schema(description = "是否特价")
    private boolean isSpecial;

    /**
     * 平台佣金
     */
    @Schema(description = "平台佣金")
    private BigDecimal platformPrice;

    /**
     * 平台佣金，用于显示
     */
    @Schema(description = "平台佣金，用于显示")
    private String platformPriceString;

    /**
     * 退还平台佣金
     */
    @Schema(description = "退还平台佣金")
    private BigDecimal backPlatformPrice;

    /**
     * 订单改价差额
     */
    @Schema(description = "订单改价差额")
    private String specialPrice;

    /**
     * 订单满减金额
     */
    @Schema(description = "订单满减金额")
    private String reductionPrice = "-";

    /**
     * 订单满折金额
     */
    @Schema(description = "订单满折金额")
    private String discountPrice = "-";

    /**
     * 通用券优惠
     */
    @Schema(description = "通用券优惠")
    private BigDecimal commonCouponPrice;

    /**
     * 通用券优惠，作为显示
     */
    @Schema(description = "通用券优惠")
    private String commonCouponPriceString;

    /**
     * 店铺券优惠金额
     */
    @Schema(description = "店铺券优惠金额")
    private String storeCouponPrice = "-";

    /**
     * 退货返还通用券补偿
     */
    @Schema(description = "退货返还通用券补偿")
    private BigDecimal commonReturnCouponPrice;

    /**
     * 退货返还通用券补偿
     */
    @Schema(description = "退货返还通用券补偿")
    private String commonReturnCouponPriceString;

    /**
     * 实付金额（分摊价）
     */
    @Schema(description = "实付金额（分摊价）")
    private BigDecimal splitPayPrice;

    /**
     * 退款状态
     */
    @Schema(description = "退款状态", contentSchema = com.wanmi.sbc.account.bean.enums.ReturnStatus.class)
    private String returnStatus;

    /**
     * 退货数量
     */
    @Schema(description = "退货数量")
    private String returnNum;

    /**
     * 应退金额
     */
    @Schema(description = "应退金额")
    private String shouldReturnPrice;

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
    private BigDecimal commissionRate = BigDecimal.ZERO;

    /**
     * 分销佣金
     */
    @Schema(description = "分销佣金")
    private BigDecimal commission = BigDecimal.ZERO;

    @Schema(description = "分销员")
    private String distributorName;

    @Schema(description = "供货商名称")
    private String providerName;

    /**
     * 供货价
     *
     */
    @Schema(description = "供货价")
    private BigDecimal supplyPrice;

    /**
     * 商品供货价
     * trade - tradeItem - price
     */
    @Schema(description = "商品供货价")
    private BigDecimal providerPrice;

    /**
     * 礼品卡金额
     */
    @Schema(description = "礼品卡抵扣金额")
    private BigDecimal giftCardPrice;

    /**
     * 该字段只用于报表导出。其他业务中为空
     */
    @Schema(description = "礼品卡-提货卡抵扣金额")
    private BigDecimal pickupGiftCardPrice;

    /**
     * 礼品卡类型
     */
    @Schema(description = "礼品卡类型")
    private GiftCardType giftCardType;

    @Schema(description = "社区团购佣金比例")
    private BigDecimal CommunityCommissionRate = BigDecimal.ZERO;

    @Schema(description = "社区团购佣金")
    private BigDecimal communityCommission = BigDecimal.ZERO;



    /**
     * 复写通用券优惠券get方法
     * @return
     */
    public String getCommonCouponPriceString(){
        if (commonCouponPrice != null){
            return commonCouponPrice.toString();
        }else{
            return "-";
        }
    }

    /**
     * 复写通用券优惠券get方法
     * @return
     */
    public String getCommonReturnCouponPriceString(){
        if (commonReturnCouponPrice != null){
            return commonReturnCouponPrice.toString();
        }else{
            return "-";
        }
    }

    /**
     * 复写特价get方法
     * @return
     */
    public String getSpecialPrice(){
        if(isSpecial){
            return specialPrice;
        }else{
            return "-";
        }
    }

    /**
     * 复写平台佣金get方法
     * @return
     */
    public String getPlatformPriceString(){
        if(null != platformPrice){
            return platformPrice.toString();
        }
        return "-";
    }

    /**
     * 复写积分抵扣get方法
     * @return
     */
    public String getPointPrice() {
        if (pointPrice != null){
            return String.format("%.2f", pointPrice);
        }else{
            return "-";
        }
    }

    /**
     * 复写佣金比例get方法
     * @return
     */
    public String getCommissionRate() {
        if (commissionRate != null){
            return commissionRate.multiply(new BigDecimal(100)).toString() + "%";
        }else{
            return "-";
        }
    }

    /**
     * 复写分销佣金get方法
     * @return
     */
    public String getCommission() {
        if (commission != null){
            return String.format("%.2f",  commission);
        }else{
            return "-";
        }
    }

    public String getCommunityCommissionRate() {
        if (CommunityCommissionRate != null){
            return CommunityCommissionRate.toString() + "%";
        }else{
            return "-";
        }
    }

    public String getCommunityCommission() {
        if (communityCommission != null){
            return String.format("%.2f",  communityCommission);
        }else{
            return "-";
        }
    }

}
