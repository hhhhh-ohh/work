package com.wanmi.sbc.common.enums;

import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 跨境商品的贸易类型
 * @author  wur
 * @date: 2021/6/8 14:29
 **/
public enum CrossTradeType {

    /**
     * 保税集货
     */
    @ApiEnumProperty("0: 保税集货")
    COLLECT_GOODS("COLLECT_GOODS","保税集货"),

    /**
     * 保税备货
     */
    @ApiEnumProperty("1: 保税备货")
    STOCK_GOODS("STOCK_GOODS","保税备货"),

    /**
     * 跨境直邮
     */
    @ApiEnumProperty("2: 跨境直邮")
    DIRECT_MAIL("DIRECT_MAIL","跨境直邮"),

    /**
     * 一般贸易
     */
    @ApiEnumProperty("3: 一般贸易")
    COMMON_TRADE("COMMON_TRADE","一般贸易");

    private String typeId;

    private String description;

    CrossTradeType(String statusId, String description) {
        this.typeId = statusId;
        this.description = description;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getDescription() {
        return description;
    }

}
