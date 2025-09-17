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
 * 汇总发货单类型
 * @author dyt
 */

@ApiEnum
@Getter
public enum DeliveryOrderSummaryType {

    @ApiEnumProperty("0：团长")
    LEADER,

    @ApiEnumProperty("1：区域")
    AREA;

    @JsonCreator
    public DeliveryOrderSummaryType fromValue(int value) {
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
    public static List<DeliveryOrderSummaryType> fromValue(String value) {
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
    public static String toValue(List<DeliveryOrderSummaryType> types) {
        if (CollectionUtils.isEmpty(types)) {
            return null;
        }
        return types.stream().map(t -> String.valueOf(t.toValue())).collect(Collectors.joining(","));
    }
}
