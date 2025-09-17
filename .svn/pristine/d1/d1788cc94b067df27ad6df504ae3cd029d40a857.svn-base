package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangwenchang
 * @Description: 配送状态
 */
@ApiEnum(dataType = "java.lang.String")
public enum DistributionState {

    @ApiEnumProperty("INIT: 待分配")
    INIT("INIT", "待分配"),

    @ApiEnumProperty("DONE: 已分配")
    DONE("DONE", "已分配"),

    @ApiEnumProperty("NONE: 无")
    NONE("NONE", "无"),

    @ApiEnumProperty("UNTREAD: 退回中")
    UNTREAD("UNTREAD", "退回中");

    private String statusId;

    private String description;

    DistributionState(String statusId, String description) {
        this.statusId = statusId;
        this.description = description;
    }

    public String getStatusId() {
        return statusId;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static DistributionState forValue(String statusId) {
        if (StringUtils.isBlank(statusId)) {
            return null;
        }
        for (DistributionState item : DistributionState.values()) {
            if (item.getStatusId().equals(statusId)) {
                return item;
            }
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        return statusId;
    }
}
