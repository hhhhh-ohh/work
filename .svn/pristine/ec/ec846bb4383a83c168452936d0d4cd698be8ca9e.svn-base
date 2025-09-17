package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 分账合作协议上传类型
 */
@ApiEnum
public enum BindContractUploadType {

    /**
     * 商户进件
     */
    @ApiEnumProperty("商户进件")
    APPLY_MER,

    /**
     * 申请分账绑定
     */
    @ApiEnumProperty("申请分账绑定")
    APPLY_BIND;

    @JsonCreator
    public static BindContractUploadType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
