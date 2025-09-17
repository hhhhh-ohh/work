package com.wanmi.sbc.vas.bean.enums;

import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

@ApiEnum
public enum ConfigType {
    @ApiEnumProperty("智能推荐关联分析设置")
    RELATED_ANALYSIS_CONFIG("related_analysis_config"),

    @ApiEnumProperty("crm智能推荐，手动推荐商品设置")
    MANUAL_RECOMMEND_GOODS_CONFIG("manual_recommend_goods_config"),

    @ApiEnumProperty("基于用户兴趣推荐设置")
    USER_INTEREST_RECOMMEND_CONFIG("user_interest_recommend_config");

    private final String value;

    ConfigType(String value){
        this.value = value;
    }

    public String toValue() {
        return this.value;
    }
}
