package com.wanmi.perseus.param;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @ClassName MarketingType
 * @description
 * @Author zhanggaolei
 * @Date 2021/2/2 9:48
 * @Version 1.0
 **/
@ApiEnum(dataType = "java.lang.String")
public enum MarketingType {

    /**
     * 满减
     */
    @ApiEnumProperty("满减优惠")
    REDUCTION(0,"满减优惠"),

    /**
     * 满折
     */
    @ApiEnumProperty("满折优惠")
    DISCOUNT(1,"满折优惠"),

    /**
     * 满赠
     */
    @ApiEnumProperty("满赠优惠")
    GIFT(2,"满赠优惠"),

    /**
     * 一口价优惠
     */
    @ApiEnumProperty("一口价优惠活动")
    BUYOUT_PRICE(3,"一口价优惠"),

    /**
     * 第二件半价优惠活动
     */
    @ApiEnumProperty("第二件半价优惠活动")
    HALF_PRICE_SECOND_PIECE(4,"第二件半价优惠活动"),

    @ApiEnumProperty("秒杀")
    FLASH_SALE(5,"秒杀"),

    @ApiEnumProperty("组合套餐")
    SUITS(6,"组合套餐"),

    @ApiEnumProperty("加价购")
    PREFERENTIAL(8,"加价购"),

    @ApiEnumProperty("拼团")
    GROUPON(101,"拼团"),

    @ApiEnumProperty("预约")
    BOOKING_SALE(102,"预约"),

    @ApiEnumProperty("全款预售")
    APPOINTMENT_SALE(103,"全款预售"),

    @ApiEnumProperty("分销")
    DISTRIBUTION(104,"分销"),

    @ApiEnumProperty("定金预售")
    DEPOSIT_SALE(105,"定金预售"),

    @ApiEnumProperty("砍价")
    BARGAIN(11,"砍价");



    MarketingType(Integer id,String desc) {
        this.desc = desc;
        this.id = id;
    }

    /**
     * 描述信息
     */
    private String desc;
    private Integer id;

    public static MarketingType fromId(int id) {
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
            case 6:
                return SUITS;
            case 8:
                return PREFERENTIAL;
            case 11:
                return BARGAIN;
            case 101:
                return GROUPON;
            case 102:
                return BOOKING_SALE;
            case 103:
                return APPOINTMENT_SALE;
            case 104:
                return DISTRIBUTION;
            case 105:
                return DEPOSIT_SALE;
            default:
                return null;
        }
    }

    public static MarketingType fromValue(String value) {
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
            case "SUITS":
                return SUITS;
            case "GROUPON":
                return GROUPON;
            case "BOOKING_SALE":
                return BOOKING_SALE;
            case "APPOINTMENT_SALE":
                return APPOINTMENT_SALE;
            case "DEPOSIT_SALE":
                return DEPOSIT_SALE;
            case "BARGAIN":
                return BARGAIN;
            case "PREFERENTIAL":
                return PREFERENTIAL;
            default:
                return null;
        }
    }

    @JsonValue
    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

}
