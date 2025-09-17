package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;

/**
 * @description 定时任务执行结果
 * @author malianfeng
 * @date 2021/9/8 17:15
 */
@ApiEnum
public enum TaskResult {

    /**
     * 执行失败
     */
    EXECUTE_FAIL,

    /**
     * 执行成功
     */
    EXECUTE_SUCCESS;

    @JsonCreator
    public static TaskResult fromValue(int ordinal) {
        return TaskResult.values()[ordinal];
    }

    @JsonValue
    public Integer toValue() {
        return this.ordinal();
    }
}

