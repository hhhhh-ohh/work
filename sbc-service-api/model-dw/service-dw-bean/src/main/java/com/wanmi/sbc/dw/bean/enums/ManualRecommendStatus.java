package com.wanmi.sbc.dw.bean.enums;

/**
 * @ClassName: com.wanmi.sbc.dw.bean.enums.ManualRecommendStatus
 * @Description: 手动推荐是否开启
 * @Author: 何军红
 * @Time: 2020/12/2 10:47
 * @Version: 1.0
 */
public enum ManualRecommendStatus {

    ON(1, "开启"),
    OFF(0, "关闭");


    private Integer code;
    private String name;

    ManualRecommendStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    public static String getValue(int code) {
        ManualRecommendStatus[] eventTypes = values();
        for (ManualRecommendStatus statisticalRange : eventTypes) {
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
