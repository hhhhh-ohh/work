package com.wanmi.sbc.empower.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

import java.util.Objects;

/**
 * @description 视频号下单场景枚举
 * @author malianfeng
 * @date 2022/4/25 13:18
 */
public enum WxSceneGroup {

    // 完成spu接口全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）")
    @ApiEnumProperty("1175:视频号商店")
    SHOP(1175, "视频号商店"),

    @ApiEnumProperty("1176:直播间")
    LIVE_ROOM_1(1176, "直播间"),

    @ApiEnumProperty("1177:直播间")
    LIVE_ROOM_2(1177, "直播间"),

    @ApiEnumProperty("1191:视频号商店")
    ACTIVITY(1191, "视频号商店"),

    @ApiEnumProperty("1195:橱窗")
    SHOW_WINDOW(1195, "橱窗");

    private final Integer code;
    private final String name;

    WxSceneGroup(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @JsonCreator
    public static WxSceneGroup fromValue(Integer code) {
        if (Objects.nonNull(code)) {
            for (WxSceneGroup item : values()) {
                if (item.code.equals(code)) {
                    return item;
                }
            }
        }
        return null;
    }

    @JsonValue
    public Integer toValue() {
        return this.code;
    }

    public String toName() {
        return this.name;
    }
}
