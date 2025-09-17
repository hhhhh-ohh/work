package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * 审核状态
 * Created by Administrator on 2017/4/18.
 */
@ApiEnum(dataType = "java.lang.String")
public enum AuditState {

    @ApiEnumProperty("0: 未审核")
    NON_CHECKED("NON_CHECKED", "未审核"),

    @ApiEnumProperty("1: 已审核")
    CHECKED("CHECKED", "已审核"),

    @ApiEnumProperty("2: 已打回")
    REJECTED("REJECTED", "已打回");

    private String statusId;

    private String description;

    AuditState(String statusId, String description) {
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
    public static AuditState fromValue(String statusId) {
        if (StringUtils.isBlank(statusId)) {
            return null;
        }
        for (AuditState auditState : AuditState.values()) {
            if (auditState.getStatusId().equals(statusId)) {
                return auditState;
            }
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        return statusId;
    }
}
