package com.wanmi.ares.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MarketingStatus {
    // 未开始
    NOT_START,

    // 进行中
    STARTED,

    // 暂停中
    PAUSED,

    // 已结束
    ENDED;

    @JsonCreator
    public MarketingStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
