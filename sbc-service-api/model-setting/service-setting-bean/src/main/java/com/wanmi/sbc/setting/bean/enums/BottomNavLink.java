package com.wanmi.sbc.setting.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 底部导航落地页枚举
 * @author malianfeng
 * @date 2022/8/1 11:22
 */
public enum BottomNavLink {

    /**
     * 首页
     */
    @ApiEnumProperty("首页")
    MAIN("main"),

    /**
     * 个人中心
     */
    @ApiEnumProperty("个人中心")
    USER_CENTER("UserCenter"),

    /**
     * 个人中心
     */
    @ApiEnumProperty("类目")
    CATEGORY("category"),

    /**
     * 个人中心
     */
    @ApiEnumProperty("全部商品")
    ALL_PRODUCT("allProduct"),

    /**
     * 分销/奖励中心
     */
    @ApiEnumProperty("分销/奖励中心")
    DISTRIBUTION("distribution"),

    /**
     * 购物车
     */
    @ApiEnumProperty("购物车")
    CART("cart"),

    /**
     * 直播/种草
     */
    @ApiEnumProperty("直播/种草")
    LIVE("live"),

    /**
     * 会员中心
     */
    @ApiEnumProperty("会员中心")
    VIP("vip"),

    /**
     * 校服订购
     */
    @ApiEnumProperty("校服订购")
    UNIFORM("uniform");

    private final String key;

    private static final Map<String, BottomNavLink> map = new HashMap<>();

    static {
        Arrays.stream(BottomNavLink.values()).forEach(t -> map.put(t.toValue(), t));
    }

    BottomNavLink(String key) {
        this.key = key;
    }

    @JsonCreator
    public static BottomNavLink fromValue(String key) {
        return map.get(key);
    }

    @JsonValue
    public String toValue() {
        return key;
    }

}
