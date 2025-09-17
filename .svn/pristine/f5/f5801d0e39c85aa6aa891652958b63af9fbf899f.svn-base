package com.wanmi.sbc.dw.bean.enums;

import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * @ClassName: com.wanmi.sbc.dw.bean.enums
 * @Description:
 * @Author: 何军红
 * @Time: 10:55 10:55
 */
@Schema
public enum AnalysisValueEnum {
    /**
     * 曝光人数：有访问并且浏览过推荐坑位数据的去重人数
     */
    @ApiEnumProperty("1:曝光人数")
    EXPOSURE_NUMBER_OF_PEOPLE(0, "exposureNumberOfPeople"),

    /**
     * 曝光商品数：被推荐坑位展示出来的去重商品数
     */
    @ApiEnumProperty("2:曝光商品数")
    EXPOSURE_NUMBER_OF_GOODS_UV(1, "exposureNumberOfGoodsUV"),

    /**
     * 曝光量：被推荐坑位展示出来的数据量(不去重)  PV
     */

    @ApiEnumProperty("3:曝光量")
    EXPOSURE_NUMBER_OF_GOODS_PV(2, "exposureNumberOfGoodsPV"),

    /**
     * 点击量：推荐坑位商品数据被点击次数
     */

    @ApiEnumProperty("4:点击量")
    CLICK_NUMBER_OF_GOODS_PV(3, "clickNumberOfGoodsPV"),

    /**
     * 点击人数：点击推荐坑位数据的去重人数
     */
    @ApiEnumProperty("5:点击人数")
    CLICK_NUMBER_OF_PEOPLE_UV(4, "clickNumberOfPeopleUV"),

    /**
     * 点击率：点击量/曝光量×1%，
     */
    @ApiEnumProperty("6:点击率")
    CLICK_PERCENT(5, "clickPercent"),

    /**
     * 平均点击次数：点击量/曝光人数
     */
    @ApiEnumProperty("7:平均点击次数")
    AVG_CNT_OF_CLICK(6, "avgCntOfClick"),

    /**
     * 加购次数：从推荐坑位点击加购的次数（推荐坑位直接加购以及推荐坑位进入商详页后再加购都包含在内）
     */
    @ApiEnumProperty("8:加购次数")
    PURCHASED_NUMBER_OF_GOODS_PV(7, "purchasedNumberOfGoodsPV"),

    /**
     * 加购人数：从推荐坑位点击加购的去重人数（推荐坑位直接加购以及推荐坑位进入商详页后再加购都包含在内）
     */
    @ApiEnumProperty("9:加购人数")
    PURCHASED_NUMBER_OF_PEOPLE_UV(8, "purchasedNumberOfPeopleUV"),

    /**
     * 下单笔数：从推荐坑位成功提交的订单数（推荐坑位立即购买以及从推荐坑位加购后2天内提交订单都包含在内）
     */
    @ApiEnumProperty("10:下单笔数")
    ORDER_NUMBER(9, "orderNumber"),

    @ApiEnumProperty("11:下单人数")
    ORDER_NUMBER_OF_PEOPLE(10, "orderNumberOfPeople"),

    /**
     * 付款笔数：统计订单中成功付款的订单数
     */
    @ApiEnumProperty("12：付款笔数")
    PAYMENT_NUMBER(11, "paymentNumber"),

    /**
     * 付款人数：统计订单中成功付款的去重人数（线上线下付款都以已付款状态为准）
     */
    @ApiEnumProperty("13：付款人数")
    PAYMENT_NUMBER_OF_PEOPLE(12, "paymentNumberOfPeople"),

    /**
     * 付款金额：统计订单中成功付款的订单总金额（线上线下付款都以已付款状态为准）
     */
    @ApiEnumProperty("14：付款金额")
    PAYMENT_AMOUNT_OF_MONEY(13, "paymentAmountOfMoney"),

    /**
     * 下单付款转化率：付款人数/下单人数×1%
     */
    @ApiEnumProperty("15:下单付款转化率")
    PERCENT_OF_ORDER_TO_PAY(14, "percentOfOrderToPay"),

    /**
     * 曝光-付款展示率：曝光人数/付款人数×1%，
     */
    @ApiEnumProperty("16:曝光-付款展示率")
    PERCENT_OF_DISPLAY_TO_PAY(15, "percentOfDisplayToPay");


    private Integer typeId;
    private String typeName;

    AnalysisValueEnum(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }
    
}
