package com.wanmi.sbc.common.enums;

import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

import java.util.Objects;

/**
 * 达达状态 0:新订单1:待接单2:待取货3:配送中4:已完成5:已取消7:已过期8:指派单9:返回妥投异常中10:妥投异常完成100:骑士到店1000:创建达达运单失败
 * @author zhangwenchang
 */
@ApiEnum(dataType = "java.lang.String")
public enum DadaOrderStatus {

    @ApiEnumProperty("0: 新订单")
    START(0, "新订单"),

    @ApiEnumProperty("1:待接单")
    WAIT_RECEIPT(1, "待接单"),

    @ApiEnumProperty("2:待取货")
    WAIT_PICKUP(2, "待取货"),

    @ApiEnumProperty("3:配送中")
    DELIVERY(3, "配送中"),

    @ApiEnumProperty("4:已完成")
    FINISH(4, "已完成"),

    @ApiEnumProperty("5:已取消")
    CANCEL(5, "已取消"),

    @ApiEnumProperty("7:已过期")
    OVERDUE(7, "已过期"),

    @ApiEnumProperty("8:指派单")
    ASSIGN(8, "指派单"),

    @ApiEnumProperty("9:妥投异常之物品返回中")
    FAULT_BACK(9, "妥投异常之物品返回中"),

    @ApiEnumProperty("10:妥投异常之物品返回完成")
    FAULT_BACK_END(10, "妥投异常之物品返回完成"),

    @ApiEnumProperty("100:骑士到店")
    DELIVERY_BACK(100, "骑士到店"),

    @ApiEnumProperty("1000:创建达达运单失败")
    FAIL(1000, "创建达达运单失败");


    private Integer statusId;

    private String description;

    DadaOrderStatus(Integer statusId, String description) {
        this.statusId = statusId;
        this.description = description;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 状态值转换为枚举
     *
     * @param statusId 状态值
     * @return 枚举
     */
    public static DadaOrderStatus chg(Integer statusId) {
        if (Objects.isNull(statusId)) {
            return null;
        }
        int len = DadaOrderStatus.values().length;
        for (int i = 0; i < len; i++) {
            DadaOrderStatus status = DadaOrderStatus.values()[i];
            if (status.statusId.equals(statusId)) {
                return status;
            }
        }
        return null;
    }
}
