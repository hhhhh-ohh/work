package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * 订单支付顺序
 */
@ApiEnum(dataType = "java.lang.String")
public enum PaymentOrder {

    @ApiEnumProperty("0: NO_LIMIT 不限")
    NO_LIMIT("NO_LIMIT", "不限"),

    @ApiEnumProperty("1: PAY_FIRST 先款后货")
    PAY_FIRST("PAY_FIRST", "先款后货");

    private String stateId;

    private String description;

    PaymentOrder(String stateId, String description) {
        this.stateId = stateId;
        this.description = description;
    }

    public String getStateId() {
        return stateId;
    }

    @JsonCreator
    public static PaymentOrder fromValue(String statusId) {
        if (StringUtils.isBlank(statusId)) {
            return null;
        }
        for (PaymentOrder status : PaymentOrder.values()) {
            if (status.getStateId().equals(statusId)) {
                return status;
            }
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        return stateId;
    }
}
