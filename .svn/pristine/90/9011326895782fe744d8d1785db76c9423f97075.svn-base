package com.wanmi.sbc.setting.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 底部导航标识枚举
 * @author malianfeng
 * @date 2022/8/1 11:22
 */
public enum BottomNavKey {

    /**
     * 导航一
     */
    @ApiEnumProperty("导航一")
    TAB_INDEX_0("tabIndex0"),

    /**
     * 导航二
     */
    @ApiEnumProperty("导航二")
    TAB_INDEX_1("tabIndex1"),

    /**
     * 导航三
     */
    @ApiEnumProperty("导航三")
    TAB_INDEX_2("tabIndex2"),

    /**
     * 导航四
     */
    @ApiEnumProperty("导航四")
    TAB_INDEX_3("tabIndex3"),

    /**
     * 导航五
     */
    @ApiEnumProperty("导航五")
    TAB_INDEX_4("tabIndex4");

    private final String key;

    private static final Map<String, BottomNavKey> map = new HashMap<>();

    static {
        Arrays.stream(BottomNavKey.values()).forEach(t -> map.put(t.toValue(), t));
    }

    BottomNavKey(String key) {
        this.key = key;
    }

    @JsonCreator
    public static BottomNavKey fromValue(String key) {
        return map.get(key);
    }

    @JsonValue
    public String toValue() {
        return key;
    }

}
