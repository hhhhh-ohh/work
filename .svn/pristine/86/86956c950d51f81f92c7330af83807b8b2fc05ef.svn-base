package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 社区拼团销售渠道
 * @author dyt
 */

@ApiEnum
@Getter
public enum CommunitySalesType {

    @ApiEnumProperty("0：自主销售")
    SELF,

    @ApiEnumProperty("1：团长帮卖")
    LEADER;

    @JsonCreator
    public CommunitySalesType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

    /**
     * 字符串转枚举
     * @param value 字符串
     * @return 类型集合
     */
    public static List<CommunitySalesType> fromValue(String value) {
        if (value == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(value.split(",")).map(v -> values()[Integer.parseInt(v)]).collect(Collectors.toList());
    }

    /**
     * 枚举转字符串
     * @param types 类型集合
     * @return 字符串
     */
    public static String toValue(List<CommunitySalesType> types) {
        if (CollectionUtils.isEmpty(types)) {
            return null;
        }
        return types.stream().map(t -> String.valueOf(t.toValue())).collect(Collectors.joining(","));
    }
}
