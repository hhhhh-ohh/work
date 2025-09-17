package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ApiEnum(dataType = "string")
public enum RepeatType {

    /**
     * 每周
     */
    @ApiEnumProperty("0: 每周")
    WEEK("week", "每周"),

    /**
     * 每月
     */
    @ApiEnumProperty("1: 每月")
    MONTH("month", "每月");

    private static Map<String, RepeatType> dataMap = new HashMap<>();

    static {
        Arrays.asList(RepeatType.values()).stream().forEach(
                t -> dataMap.put(t.getStateId(), t)
        );
    }

    private String stateId;

    private String description;

    RepeatType(String stateId, String description) {
        this.stateId = stateId;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RepeatType forValue(String stateId) {
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
