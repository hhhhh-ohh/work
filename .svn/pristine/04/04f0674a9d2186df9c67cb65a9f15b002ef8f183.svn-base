package com.wanmi.sbc.common.enums;

import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/***
 * 商品详情类型
 * @author zhengyang
 * @since 2021-06-07
 */
public enum GoodsDetailType {
    /***
     * PC端商品详情枚举
     */
    @ApiEnumProperty("0: PC端")
    PC("PC", "PC端"),

    /***
     * 移动端商品详情枚举
     */
    @ApiEnumProperty("1: 移动端")
    MOBILE("MOBILE", "Mobile端"),

    /***
     * 微信端商品详情枚举
     */
    @ApiEnumProperty("2: 微信端")
    WECHAT("WECHAT", "微信端");

    private final String type;

    private final String description;

    GoodsDetailType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
