package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 砍价状态
 */
public enum BargainStatus {

    /**
     * 砍价进行中
     */
    PROGRESS,
    /**
     * 砍价成功去下单
     */
    COMPLETED,
    /**
     * 活动已结束
     */
    STOP,
    /**
     * 已完成
     */
    END;


    @JsonCreator
    public BargainStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
