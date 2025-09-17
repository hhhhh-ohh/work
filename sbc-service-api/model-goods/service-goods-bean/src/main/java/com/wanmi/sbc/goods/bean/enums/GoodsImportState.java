package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author xuyunpeng
 * @className GoodsImportState
 * @description 商品库导入状态
 * @date 2022/7/18 1:52 PM
 **/
@ApiEnum
public enum GoodsImportState {

    @ApiEnumProperty("0：未导入")
    NOT_IMPORT,

    @ApiEnumProperty("1：已导入")
    IMPORT,

    @ApiEnumProperty("2：不可导入")
    CAN_NOT_IMPORT;
    @JsonCreator
    public GoodsImportState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
