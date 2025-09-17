package com.wanmi.sbc.empower.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 分账平台类型枚举类
 **/
@ApiEnum
public enum LedgerType {

    /**
     * 拉卡拉分账平台
     */
    @ApiEnumProperty(" 0：拉卡拉分账平台")
    LAKALA_LEDGER(LedgerType.LAKALA_LEDGER_SERVICE);


    private String ledgerService;

    public static final String LAKALA_LEDGER_SERVICE = "lakalaLedgerServiceImpl";

    LedgerType(String ledgerService) {
        this.ledgerService = ledgerService;
    }

    public String getLedgerService() {
        return ledgerService;
    }

    @JsonCreator
    public static LedgerType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
