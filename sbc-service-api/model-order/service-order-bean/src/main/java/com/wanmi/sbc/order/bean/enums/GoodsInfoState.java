package com.wanmi.sbc.order.bean.enums;

import com.wanmi.sbc.common.annotation.ApiEnumProperty;

public enum GoodsInfoState {

    @ApiEnumProperty("0: 未收到货")
    NOT_RECEIVED("NOT_RECEIVED","未收到货"),

    @ApiEnumProperty("1: 已收到货")
    RECEIVED("RECEIVED","已收到货");

    private String stateId;

    private String description;

    GoodsInfoState(String stateId, String description) {
        this.stateId = stateId;
        this.description = description;
    }

    public String getStateId() {
        return stateId;
    }

    public String getDescription() {
        return description;
    }
}
