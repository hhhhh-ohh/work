package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 分账错误记录类型
 */
@ApiEnum
public enum LedgerFunctionType {

    /**
     * 申请账户
     */
    @ApiEnumProperty("0、申请账户")
    CREATE_ACCOUNT(LedgerFunctionType.CREATE_ACCOUNT_FUNCTION),

    /**
     * 绑定账户关系
     */
    @ApiEnumProperty("1、绑定账户关系")
    BIND_ACCOUNT(LedgerFunctionType.BIND_ACCOUNT_FUNCTION);

    private String ledgerFunction;

    public static final String CREATE_ACCOUNT_FUNCTION = "lakaLaCreateAccountService";

    public static final String BIND_ACCOUNT_FUNCTION = "lakalaBindService";

    LedgerFunctionType(String ledgerFunction) {
        this.ledgerFunction = ledgerFunction;
    }

    public String getLedgerFunction() {
        return ledgerFunction;
    }

    @JsonCreator
    public static LedgerFunctionType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
