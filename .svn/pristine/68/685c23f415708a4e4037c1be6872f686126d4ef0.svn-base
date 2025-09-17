package com.wanmi.sbc.marketing.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 卡券销售状态
 * @author xuyunpeng
 */
@ApiEnum
public enum CardSaleState {

    /**
     * 无状态
     */
    @ApiEnumProperty("0：无状态")
    NO_STATE,

    /**
     * 未过销售期
     */
    @ApiEnumProperty("1: 未过销售期")
    NOT_EXPIRED,

    /**
     * 已过销售期
     */
    @Schema(description = "2: 已过销售期")
    EXPIRED;

    @JsonCreator
    public CardSaleState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
