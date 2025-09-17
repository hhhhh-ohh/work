package com.wanmi.sbc.customer.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/***
 * 店铺经营范围类型枚举
 * @className O2oStoreOfflineInfo
 * @author zhengyang
 * @date 2021/6/30 16:24
 **/
public enum StoreBusinessScopeTypeEnum {

    /***
     * 全国
     */
    NATIONAL,

    /***
     * 全省
     */
    PROVINCE,

    /***
     * 同城
     */
    CITY,

    /**
     * 自定义
     */
    CUSTOM;

    @JsonCreator
    public static StoreBusinessScopeTypeEnum fromValue(int ordinal) {
        return StoreBusinessScopeTypeEnum.values()[ordinal];
    }

    @JsonValue
    public Integer toValue() {
        return this.ordinal();
    }

}
