package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import org.apache.commons.lang3.StringUtils;

@ApiEnum(dataType = "java.lang.String")
public enum DeliverStatus {

    @ApiEnumProperty("0: 未发货")
    NOT_YET_SHIPPED("NOT_YET_SHIPPED","未发货"),

    @ApiEnumProperty("1: 已发货")
    SHIPPED("SHIPPED","已发货"),

    @ApiEnumProperty("2: 部分发货")
    PART_SHIPPED("PART_SHIPPED","部分发货"),

    @ApiEnumProperty("3: 作废")
    VOID("VOID","作废");

    private String statusId;

    private String description;

    DeliverStatus(String statusId, String description) {
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
    public static DeliverStatus fromValue(String statusId) {
        if (StringUtils.isBlank(statusId)) {
            return null;
        }
        for (DeliverStatus status : DeliverStatus.values()) {
            if (status.getStatusId().equals(statusId)) {
                return status;
            }
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        return statusId;
    }
}
