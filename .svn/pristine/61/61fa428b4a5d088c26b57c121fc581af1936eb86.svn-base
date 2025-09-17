package com.wanmi.sbc.account.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 授信还款记录还款状态
 * @author cl
 */
public enum CreditRepayStatus {

    /**
     * 0 待还款
     */
    @ApiEnumProperty("待还款")
    WAIT("待还款"),

    /**
     * 1 还款成功
     */
    @ApiEnumProperty("还款成功")
    FINISH("还款成功"),

    /**
     * 2 已作废
     */
    @ApiEnumProperty("已作废")
    VOID("已作废"),

    /**
     * 3 待审核
     */
    @ApiEnumProperty("待审核")
    AUDIT("待审核"),

    /**
     * 4 审核驳回
     */
    @ApiEnumProperty("审核驳回")
    TURN_DOWN("审核驳回");

    private String desc;

    CreditRepayStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public CreditRepayStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

    public static List<CreditRepayStatus> getCheckStatus() {
        List<CreditRepayStatus> list = new ArrayList<>();
        list.add(WAIT);
        list.add(AUDIT);
        return list;
    }
}
