package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author lvzhenwei
 * @Description 订单类型枚举，由于现在订单类型比较多，所以用orderType来区分订单的类型
 * @Date 15:16 2019/5/14
 * @Param
 * @return
 **/
@ApiEnum(dataType = "java.lang.String")
public enum OrderType {

    @ApiEnumProperty("0: 普通订单")
    NORMAL_ORDER("NORMAL_ORDER", "普通订单"),

    @ApiEnumProperty("1: 积分订单")
    POINTS_ORDER("POINTS_ORDER", "积分订单"),

    @ApiEnumProperty("2: 所有订单")
    ALL_ORDER("ALL_ORDER", "所有订单"),

    @ApiEnumProperty("3: 定金预售")
    EARNEST_MONEY_BOOK("EARNEST_MONEY_BOOK", "定金预售");

    private String orderTypeId;

    private String description;

    OrderType(String orderTypeId, String description) {
        this.orderTypeId = orderTypeId;
        this.description = description;
    }

    public String getOrderTypeId() {
        return orderTypeId;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static OrderType fromValue(String statusId) {
        if (StringUtils.isBlank(statusId)) {
            return null;
        }
        for (OrderType status : OrderType.values()) {
            if (status.getOrderTypeId().equals(statusId)) {
                return status;
            }
        }
        return null;
    }
}
