package com.wanmi.sbc.order.bean.enums;

import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 时间状态
 * Created by xufeng on 29/5/2021.
 */
@ApiEnum
public enum TimeState {

    @ApiEnumProperty("0: SEVEN_DAY 7天内")
    SEVEN_DAY("SEVEN_DAY", "7天内"),

    @ApiEnumProperty("1: ONE_MONTH 一个月内")
    ONE_MONTH("ONE_MONTH", "一个月内"),

    @ApiEnumProperty("2: THREE_MONTH 三个月内")
    THREE_MONTH("THREE_MONTH", "三个月内");

    TimeState(String stateId, String description){
        this.stateId = stateId;
        this.description = description;
    }

    private String stateId;

    private String description;

    public String getStateId() {
        return stateId;
    }

    public String getDescription() {
        return description;
    }

}
