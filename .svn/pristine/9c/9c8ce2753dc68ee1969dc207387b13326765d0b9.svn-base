package com.wanmi.sbc.empower.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 拉卡拉附近类型
 */
@ApiEnum
public enum AttType {

    /**
     * 法人身份证正面
     */
    @ApiEnumProperty("法人身份证正面")
    FR_ID_CARD_FRONT,

    /**
     * 法人身份证反面
     */
    @ApiEnumProperty("法人身份证反面")
    FR_ID_CARD_BEHIND,

    /**
     * 身份证正面
     */
    @ApiEnumProperty("身份证正面")
    ID_CARD_FRONT,

    /**
     * 身份证反面
     */
    @ApiEnumProperty("身份证反面")
    ID_CARD_BEHIND,

    /**
     * 银行卡
     */
    @ApiEnumProperty("银行卡")
    BANK_CARD,


    /**
     * 营业执照
     */
    @ApiEnumProperty("营业执照")
    BUSINESS_LICENCE,

    /**
     * 商户门头照
     */
    @ApiEnumProperty("商户门头照")
    MERCHANT_PHOTO,

    /**
     * 商铺内部照片
     */
    @ApiEnumProperty("商铺内部照片")
    SHOPINNER,


    /**
     * 电子协议
     */
    @ApiEnumProperty("电子协议")
    NETWORK_XY,


    /**
     * 其他
     */
    @ApiEnumProperty("其他")
    OTHERS;

    @JsonCreator
    public AttType fromValue(String name) {
        return valueOf(name);
    }


    @JsonValue
    public String toValue() {
        return this.name();
    }

}
