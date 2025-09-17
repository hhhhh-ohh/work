package com.wanmi.sbc.account.bean.enums;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import com.wanmi.sbc.common.enums.SettleStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

/**
 * @description 拉卡拉分账状态
 * @author edz
 * @date 2022/7/15 10:19
 */
@ApiEnum
public enum LakalaLedgerStatus {
    @ApiEnumProperty("未结算")
    NOT_SETTLED("未结算"),
    @ApiEnumProperty("分账中")
    PROCESSING("分账中"),
    @ApiEnumProperty("分账成功")
    SUCCESS("分账成功"),
    @ApiEnumProperty("分账失败")
    FAIL("分账失败"),
    @ApiEnumProperty("分账部分成功")
    PARTIAL_SUCCESS("分账部分成功"),
    @ApiEnumProperty("分账余额不足")
    INSUFFICIENT_AMOUNT("分账余额不足"),
    @ApiEnumProperty("线下分账")
    OFFLINE("线下分账");

    private String desc;

    @JsonCreator
    public static LakalaLedgerStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

    @WritingConverter
    public enum SettleStatusToIntegerConverter implements Converter<LakalaLedgerStatus, Integer> {
        INSTANCE;
        @Override
        public Integer convert(LakalaLedgerStatus source) {
            return source.toValue();
        }
    }

    @ReadingConverter
    public enum IntegerToSettleStatusConverter implements Converter<Integer, LakalaLedgerStatus> {
        INSTANCE;
        @Override
        public LakalaLedgerStatus convert(Integer source) {
            return fromValue(source);
        }
    }

    LakalaLedgerStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
