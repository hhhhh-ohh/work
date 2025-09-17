package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.enums.CouponDiscountMode;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.CouponType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午3:33 2018/9/29
 * @Description: 订单优惠券
 */
@Schema
@Data
public class TradeCouponVO extends BasicResponse {

    private static final long serialVersionUID = 5710360546802622943L;

    /**
     * 优惠券码id
     */
    @Schema(description = "优惠券码id")
    private String couponCodeId;

    /**
     * 优惠券码值
     */
    @Schema(description = "优惠券码值")
    private String couponCode;

    /**
     * 优惠券关联的商品
     */
    @Schema(description = "优惠券关联的商品id集合")
    private List<String> goodsInfoIds;

    /**
     * 优惠券类型
     */
    @Schema(description = "优惠券类型")
    private CouponType couponType;

    /**
     * 优惠券营销活动
     */
    @Schema(description = "优惠券营销活动")
    private CouponMarketingType couponMarketingType;

    /**
     * 运费券是否包邮
     */
    @Schema(description = "couponDiscountMode")
    private CouponDiscountMode couponDiscountMode;

    /**
     * 最大优惠金额限制（结合满折券使用）
     */
    @Schema(description = "最大优惠金额限制（结合满折券使用）")
    private BigDecimal maxDiscountLimit;

    /**
     * 优惠金额
     */
    @Schema(description = "优惠金额")
    private BigDecimal discountsAmount;

    /**
     * 购满多少钱
     */
    @Schema(description = "购满多少钱")
    private BigDecimal fullBuyPrice;

    public static void main(String[] args) {
        /*for (int i = 100; i < 1000; i++) {
            //百分位
            int p = i / 100;
            //十分位
            int t = i % 100 / 10;
            //个分位
            int c = i % 100 % 10;
            double total = Math.pow(p, 3) + Math.pow(t, 3) + Math.pow(c, 3);
            if (total == (double) i) {
                System.out.println(i);
            }
        }*/
//        char[] a = "complete".toCharArray();
//        String b = "meter";
//        StringBuilder temp = new StringBuilder();
//        for (int i = 0; i < a.length; i++) {
//            temp = new StringBuilder();
//            if (b.contains(a[i]+"")) {
//                temp = new StringBuilder(String.valueOf(a[i]));
//                for (int j = i + 1; j < b.length(); j++) {
//                    StringBuilder t = new StringBuilder();
//                    if (!b.contains(t.append(temp.toString()).append(a[j]))) {
//                        break;
//                    }
//                    temp.append(a[j]);
//                }
//            }
//        }
    }

}
