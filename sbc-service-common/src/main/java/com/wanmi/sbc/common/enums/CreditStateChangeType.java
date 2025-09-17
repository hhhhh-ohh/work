package com.wanmi.sbc.common.enums;

import java.util.Arrays;
import java.util.Objects;

/***
 * 授信账户状态变更事件
 * @author zhengyang
 * @since 2021/3/11 18:11
 */
public enum CreditStateChangeType {
    AUDIT_PASS(0, "授信通过"), AMOUNT_CHANGE_PASS(1, "额度表更申请通过"),
    PAY(2, "授信支付"), REFUND(3, "授信退款"),
    REPAY(4, "授信还款"), EXPIRED(5, "授信过期"), RESTORE(6, "额度恢复");
    /***
     * 类型
     */
    private final Integer type;
    /***
     * 说明
     */
    private final String desc;

    CreditStateChangeType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static CreditStateChangeType getByType(Integer type) {
        if (Objects.nonNull(type)) {
            for (CreditStateChangeType value : CreditStateChangeType.values()) {
                if (value.getType().equals(type)) {
                    return value;
                }
            }
        }
        return null;
    }
}
