package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description  操作类型：0.商品信息变更 1.价格变更 2.状态变更 3.其他变更
 * @author  wur
 * @date: 2021/9/10 9:28
 **/
@ApiEnum
public enum GoodsEditType {
    @ApiEnumProperty("0：商品信息变更")
    INFO_EDIT,

    @ApiEnumProperty("1: 价格变更")
    PRICE_EDIT,

    @ApiEnumProperty("2: 状态变更")
    STATUS_EDIT,

    @ApiEnumProperty("3: 其他变更")
    OTHER_EDIT,
    ;
    @JsonCreator
    public GoodsEditType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
