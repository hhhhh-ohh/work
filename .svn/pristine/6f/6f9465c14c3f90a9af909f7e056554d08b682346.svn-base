package com.wanmi.sbc.dbreplay.bean.capture.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @ClassName ReplayTaype
 * @Description TODO
 * @Author zhanggaolei
 * @Date 2021/1/29 10:45
 * @Version 1.0
 **/
public enum ReplayType {

    INSERT_REPLAY,
    UPDATE_REPLAY;
    @JsonCreator
    public static ReplayType forValue(String name) {
        return ReplayType.valueOf(name);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
