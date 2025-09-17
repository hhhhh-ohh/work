package com.wanmi.sbc.dw.bean.enums;

/**
 * @ClassName: com.wanmi.sbc.dw.bean.enums.Item
 * @Description:
 * @Author: 何军红
 * @Time: 2020/12/1 18:13
 * @Version: 1.0
 */
public enum Item {

    GOODS(0, "商品"),
    CATE(1, "类目"),
    BRAND(2, "品牌");

    private Integer code;
    private String name;

    Item(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getValue(int code) {
        Item[] eventTypes = values();
        for (Item statisticalRange : eventTypes) {
            if (statisticalRange.code == code) {
                return statisticalRange.getName();
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
