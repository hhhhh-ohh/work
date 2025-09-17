package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;

/**
 * @description 定时任务业务类型
 * @author malianfeng
 * @date 2021/9/8 17:15
 */
@ApiEnum
public enum TaskBizType {

    /**
     * 精准发券
     */
    PRECISION_VOUCHERS,

    /**
     * 消息发送
     */
    MESSAGE_SEND,

    /**
     * 商品调价
     */
    PRICE_ADJUST,

    /**
     * 商家公告发送
     */
    STORE_NOTICE_SEND;

    @JsonCreator
    public static TaskBizType fromValue(int ordinal) {
        return TaskBizType.values()[ordinal];
    }

    @JsonValue
    public Integer toValue() {
        return this.ordinal();
    }
}

