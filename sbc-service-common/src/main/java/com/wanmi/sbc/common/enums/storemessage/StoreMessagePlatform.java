package com.wanmi.sbc.common.enums.storemessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import com.wanmi.sbc.common.enums.Platform;

/**
 * @description 商家消息平台类型枚举
 * @author malianfeng
 * @date 2022/7/11 13:58
 */
public enum StoreMessagePlatform {

    @ApiEnumProperty(" 0:BOSS")
    BOSS,

    @ApiEnumProperty(" 1:商家")
    SUPPLIER,

    @ApiEnumProperty(" 2:供应商")
    PROVIDER;

    @JsonCreator
    public static StoreMessagePlatform fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

    public static StoreMessagePlatform fromValue(Platform platform) {
        switch (platform) {
            case PLATFORM: return StoreMessagePlatform.BOSS;
            case SUPPLIER: return StoreMessagePlatform.SUPPLIER;
            case PROVIDER: return StoreMessagePlatform.PROVIDER;
            default: return null;
        }
    }

}

