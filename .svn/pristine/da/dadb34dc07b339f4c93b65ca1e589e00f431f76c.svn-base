package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import lombok.Getter;

@ApiEnum
@Getter
public enum WechatShelveStatus {

    @ApiEnumProperty("初始值")
    init(0),
    @ApiEnumProperty("上架")
    SHELVE(5),
    @ApiEnumProperty("自主下架")
    UN_SHELVE(11),
    @ApiEnumProperty("违规下架")
    VIOLATION_UN_SHELVE(13);

    private Integer value;

   WechatShelveStatus(Integer value) {
        this.value = value;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static WechatShelveStatus fromValue(Integer value) {
        for (WechatShelveStatus wechatShelveStatus : WechatShelveStatus.values()) {
            if (wechatShelveStatus.value.equals(value)) {
                return wechatShelveStatus;
            }
        }
        return null;
    }

    @JsonValue
    public int toValue() {
        return this.value;
    }

}
