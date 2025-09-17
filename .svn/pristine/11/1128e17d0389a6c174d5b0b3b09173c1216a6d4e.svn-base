package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import lombok.NoArgsConstructor;

@ApiEnum
@NoArgsConstructor
public enum StateDesc {

    @ApiEnumProperty("0: 商品已禁售")
    FORBID_SALE(0, "商品已禁售"),

    @ApiEnumProperty("1: 商品已下架")
    UNDERCARRIAGE(1, "商品已下架"),

    @ApiEnumProperty("2: 部分商品已下架")
    PART_UNDERCARRIAGE(2, "部分商品已下架"),

    @ApiEnumProperty("3: 商品库存小于最低期数")
    OUT_STOCK(3, "商品库存小于最低期数"),

    @ApiEnumProperty("4: 部分商品库存小于最低期数")
    PART_OUT_STOCK(4, "部分商品库存小于最低期数"),

    @ApiEnumProperty("5: 商品不可售")
    NOT_SALE(5, "商品不可售"),

    @ApiEnumProperty("6: 部分商品不可售")
    PART_NOT_SALE(6, "部分商品不可售"),

    @ApiEnumProperty("7: 商品已删除")
    ALREADY_DELETE(7, "商品已删除"),

    @ApiEnumProperty("8: 部分商品已删除")
    PART_ALREADY_DELETE(8, "部分商品已删除");

    private Integer type;

    private String desc;

    StateDesc(Integer type, String desc){
        this.type = type;
        this.desc = desc;
    }

    @JsonCreator
    public static StateDesc fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public Integer toValue() {
        return this.ordinal();
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
