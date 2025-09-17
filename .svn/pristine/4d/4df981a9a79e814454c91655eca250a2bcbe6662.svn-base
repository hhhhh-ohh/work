package com.wanmi.ares.view.coupon;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @ClassName CouponOverviewView
 * @description
 * @Author zhanggaolei
 * @Date 2021/1/14 11:33
 * @Version 1.0
 **/
@Data
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponOverviewView implements Serializable {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 支付金额
     */
    @Schema(description = "支付金额")
    private BigDecimal payMoney;

    /**
     * 优惠金额
     */
    @Schema(description = "优惠金额")
    private BigDecimal discountMoney;

    /**
     * roi
     */
    @Schema(description = "roi")
    private String roi;

    /**
     * 支付件数
     */
    @Schema(description = "支付件数")
    private Long payGoodsCount;

    /**
     * 支付订单数
     */
    @Schema(description = "支付订单数")
    private Long payTradeCount;

    /**
     * 连带率
     */
    @Schema(description = "连带率")
    private BigDecimal jointRate;

    /**
     * 老用户数
     */
    @Schema(description = "老用户数")
    private Long oldCustomerCount;

    /**
     * 新用户数
     */
    @Schema(description = "新用户数")
    private Long newCustomerCount;

    /**
     * 营销支付人数
     */
    @Schema(description = "营销支付人数")
    private Long payCustomerCount;

    /**
     * 领取张数
     */
    @Schema(description = "领取张数")
    private Long acquireCount;

    /**
     * 领取人数
     */
    @Schema(description = "领取人数")
    private Long acquireCustomerCount;

    /**
     * 使用张数
     */
    @Schema(description = "使用张数")
    private Long useCount;

    /**
     * 使用人数
     */
    @Schema(description = "使用人数")
    private Long useCustomerCount;

    /**
     * 使用率
     */
    @Schema(description = "使用率")
    private BigDecimal useRate;

    /**
     * x轴的时间
     */
    @Schema(description = "x轴的时间")
    private String xDate;

    // 客单价
    @Schema(description = "客单价）")
    private BigDecimal customerPrice;

    public BigDecimal getRoi(){
        BigDecimal _roi = null;
        if(discountMoney.compareTo(BigDecimal.ZERO)!=0){
            _roi = payMoney.divide(discountMoney,2, RoundingMode.FLOOR);
        }
        return _roi;
    }

    public BigDecimal getJointRate(){
        BigDecimal _jointRate = null;
        if(payTradeCount != 0){
            _jointRate = new BigDecimal(payGoodsCount).divide(new BigDecimal(payTradeCount),2, RoundingMode.FLOOR);
        }
        return _jointRate;
    }

    public BigDecimal getUseRate(){
        BigDecimal _useRate = null;
        if(acquireCustomerCount != 0){
            _useRate = new BigDecimal(useCustomerCount).multiply(new BigDecimal(100)).divide(new BigDecimal(acquireCustomerCount),2, RoundingMode.FLOOR);
        }
        return _useRate;
    }

    public Long getPayCustomerCount(){
        return oldCustomerCount+newCustomerCount;
    }

    public BigDecimal getCustomerPrice(){
        BigDecimal _customerPrice = null;
        long _payCustomerCount = oldCustomerCount+newCustomerCount;
        if(_payCustomerCount != 0){
            _customerPrice = payMoney.divide(new BigDecimal(_payCustomerCount),2, RoundingMode.FLOOR);
        }
        return _customerPrice;
    }

}
