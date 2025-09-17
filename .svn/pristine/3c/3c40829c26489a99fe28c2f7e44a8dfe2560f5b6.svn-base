package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author zhanggaolei
 */

public enum MarketingPluginType {
    /**
     * 满减
     */
    REDUCTION(0,"M_0_REDUCTION"),

    /**
     * 满折
     */
    DISCOUNT(1,"M_1_DISCOUNT"),

    /**
     * 满赠
     */
    GIFT(2,"M_2_GIFT"),

    /**
     * 一口价优惠
     */
    BUYOUT_PRICE(3,"M_3_BUYOUT_PRICE"),

    /**
     * 第二件半价优惠活动
     */
    HALF_PRICE_SECOND_PIECE(4,"M_4_HALF_PRICE_SECOND_PIECE"),

    /**
     * 秒杀
     */
    FLASH_SALE(5,"M_5_FLASH_SALE"),

    /**
     * 组合套餐
     */
    SUITS(6,"M_6_SUITS"),

    /**
     * 满返
     */
    RETURN(7,"M_7_RETURN"),

    /**
     * 加价购
     */
    PREFERENTIAL(8,"M_8_PREFERENTIAL"),

    /**
     *拼团
     */
    GROUPON(101,"M_101_GROUPON"),

    /**
     *预售
     */
    BOOKING_SALE(102,"M_102_BOOKING_SALE"),

    /**
     *预约
     */
    APPOINTMENT_SALE(103,"M_103_APPOINTMENT_SALE"),

    /**
     *分销
     */
    DISTRIBUTION(104,"M_104_DISTRIBUTION"),

   /* *//**
     *定金预售
     *//*
    DEPOSIT_SALE(105,"M_105_DEPOSIT_SALE"),*/

    /**
     * 优惠券
     */
    COUPON(106,"M_106_COUPON"),

    /**
     * 会员等级价
     */
    CUSTOMER_LEVEL(107,"M_107_CUSTOMER_LEVEL"),

    /**
     * 会员价
     */
    CUSTOMER_PRICE(108,"M_108_CUSTOMER_PRICE"),

    /**
     * 企业价
     */
    ENTERPRISE_PRICE(109,"M_109_ENTERPRISE_PRICE"),

    /**
     * 积分+现金
     */
    POINT_AND_CASH(110,"M_110_POINT_AND_CASH"),

    /**
     * 限时抢购，同秒杀互斥（营销级别相同）
     */
    FLASH_PROMOTION(111,"M_17_FLASH_PROMOTION"),

    /**
     * 付费会员价
     */
    PAYING_MEMBER(112, "M_112_PAYING_MEMBER"),

    /**
     * 商品砍价
     */
    GOODS_BARGAIN(113, "M_112_GOODS_BARGAIN"),

    /**
     * 新人专享券
     */
    NEW_COMER_COUPON(114,"M_114_NEW_COMER_COUPON"),


    /**
     * 周期购
     */
    BUY_CYCLE(115,"M_115_BUY_CYCLE"),

    OTHER(999,"M_999_OTHER");

    MarketingPluginType(Integer id,String serviceType) {
        this.id = id;
        this.serviceType = serviceType;
    }

    MarketingPluginType(Integer id) {
        this.id = id;
    }

    /**
     * 描述信息
     */
    private Integer id;
    private String serviceType;

    @JsonCreator
    public static MarketingPluginType fromId(int id) {
        switch (id){
            case 0:
                return REDUCTION;
            case 1:
                return DISCOUNT;
            case 2:
                return GIFT;
            case 3:
                return BUYOUT_PRICE;
            case 4:
                return HALF_PRICE_SECOND_PIECE;
            case 5:
                return FLASH_SALE;
            case 111:
                return FLASH_PROMOTION;
            case 6:
                return SUITS;
            case 7:
                return RETURN;
            case 8:
                return PREFERENTIAL;
            case 101:
                return GROUPON;
            case 102:
                return BOOKING_SALE;
            case 103:
                return APPOINTMENT_SALE;
            case 104:
                return DISTRIBUTION;
            case 106:
                return COUPON;

            case 107:
                return CUSTOMER_LEVEL;
            case 108:
                return CUSTOMER_PRICE;
            case 109:
                return ENTERPRISE_PRICE;
            case 110:
                return POINT_AND_CASH;
            case 112:
                return PAYING_MEMBER;
            case 114:
                return NEW_COMER_COUPON;
            case 115:
                return BUY_CYCLE;
            default:
                return OTHER;
        }
    }

    public static MarketingPluginType fromValue(MarketingType marketingType) {
        switch (marketingType.toValue()){
            case 0:
                return REDUCTION;
            case 1:
                return DISCOUNT;
            case 2:
                return GIFT;
            case 3:
                return BUYOUT_PRICE;
            case 4:
                return HALF_PRICE_SECOND_PIECE;
            case 7:
                return RETURN;
            case 8:
                return PREFERENTIAL;
            case 115:
                return BUY_CYCLE;
            default:
                return OTHER;
        }
    }

    public static MarketingPluginType fromValue(String value) {
        switch (value){
            case "REDUCTION":
                return REDUCTION;
            case "DISCOUNT":
                return DISCOUNT;
            case "GIFT":
                return GIFT;
            case "BUYOUT_PRICE":
                return BUYOUT_PRICE;
            case "HALF_PRICE_SECOND_PIECE":
                return HALF_PRICE_SECOND_PIECE;
            case "FLASH_SALE":
                return FLASH_SALE;
            case "FLASH_PROMOTION":
                return FLASH_PROMOTION;
            case "SUITS":
                return SUITS;
            case "GROUPON":
                return GROUPON;
            case "BOOKING_SALE":
                return BOOKING_SALE;
            case "APPOINTMENT_SALE":
                return APPOINTMENT_SALE;
            case "DISTRIBUTION":
                return DISTRIBUTION;
            case "COUPON":
                return COUPON;

            case "CUSTOMER_LEVEL":
                return CUSTOMER_LEVEL;
            case "CUSTOMER_PRICE":
                return CUSTOMER_PRICE;
            case "ENTERPRISE_PRICE":
                return ENTERPRISE_PRICE;

            case "POINT_AND_CASH":
                return POINT_AND_CASH;
            case "RETURN":
                return RETURN;
            case "PAYING_MEMBER":
                return PAYING_MEMBER;
            case "NEW_COMER_COUPON":
                return NEW_COMER_COUPON;
            case "BUY_CYCLE":
                return BUY_CYCLE;
            case "PREFERENTIAL":
                return PREFERENTIAL;
            default:
                return OTHER;
        }
    }

    @JsonValue
    public int getId() {
        return id;
    }

    public String getServiceType(){
        return serviceType;
    }

    public String getDescription(){
        switch (this){
            case REDUCTION:
                return "满减";
            case DISCOUNT:
                return "满折";
            case GIFT:
                return "满赠";
            case BUYOUT_PRICE:
                return "打包一口价";
            case HALF_PRICE_SECOND_PIECE:
                return "第二件半价";
            case FLASH_SALE:
                return "秒杀";
            case FLASH_PROMOTION:
                return "限时购";
            case SUITS:
                return "组合套餐";
            case GROUPON:
                return "拼团";
            case BOOKING_SALE:
                return "预约";
            case APPOINTMENT_SALE:
                return "全款预售";

            case DISTRIBUTION:
                return "分销";
            case COUPON:
                return "优惠券";
            case CUSTOMER_LEVEL:
                return "会员等级价";
            case CUSTOMER_PRICE:
                return "会员价";
            case ENTERPRISE_PRICE:
                return "企业价";
            case POINT_AND_CASH:
                return "积分+现金";
            case RETURN:
                return "满返";
            case NEW_COMER_COUPON:
                return "新人专享券";
            case BUY_CYCLE:
                return "周期购";
            case PREFERENTIAL:
                return "加价购";
            default:
                return "其他";
        }
    }

}
