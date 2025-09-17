package com.wanmi.sbc.dw.bean.enums;

/**
 * @ClassName: com.wanmi.sbc.dw.bean.enums.RecommendType
 * @Description:
 * @Author: 何军红
 * @Time: 2020/12/1 17:56
 * @Version: 1.0
 */
public enum RecommendType {

    HOT(0, "热门推荐"),
    RELATION(1, "相关性推荐"),
    INTEREST(2, "标签兴趣推荐"),
    CB_SIMILAR(3, "商品内容相似度推荐"),
    CF_SIMILAR(4, "协同过滤商品相似度"),
    CF_ITEM(5, "协同过滤用户推荐商品");
    private Integer code;
    private String name;

    RecommendType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getValue(int code) {
        RecommendType[] eventTypes = values();
        for (RecommendType statisticalRange : eventTypes) {
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
