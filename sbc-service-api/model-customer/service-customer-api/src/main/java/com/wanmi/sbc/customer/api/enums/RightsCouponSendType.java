package com.wanmi.sbc.customer.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuyunpeng
 * @className RightsCouponSendType
 * @description 会员权益-券礼包发放类型
 * @date 2022/5/13 4:58 PM
 **/
@ApiEnum(dataType = "string")
public enum RightsCouponSendType {

    /**
     * 只发一次
     */
    @ApiEnumProperty("0: 只发一次")
    ISSUE_ONCE("issueOnce", "只发一次"),

    /**
     * 每月X号
     */
    @ApiEnumProperty("1: 每月X号")
    ISSUE_MONTHLY("issueMonthly", "每月X号"),

    /**
     * 重复周期
     */
    @ApiEnumProperty("2:  重复周期")
    REPEAT("repeat", "重复周期");

    private static Map<String, RightsCouponSendType> dataMap = new HashMap<>();

    static {
        Arrays.asList(RightsCouponSendType.values()).stream().forEach(
                t -> dataMap.put(t.getStateId(), t)
        );
    }

    private String stateId;

    private String description;

    RightsCouponSendType(String stateId, String description) {
        this.stateId = stateId;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RightsCouponSendType forValue(String stateId) {
        return dataMap.get(stateId);
    }

    @JsonValue
    public String toValue() {
        return this.getStateId();
    }

    public String getStateId() {
        return stateId;
    }

    public String getDescription() {
        return description;
    }
}
