package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

/**
 * 入账失败原因
 */
@ApiEnum(dataType = "java.lang.String")
public enum FailReasonFlag {
    /**
     * 0:非有效邀新
     */
      @ApiEnumProperty("入账类型 0:非有效邀新")
      INVALID("非有效邀新"),

    /**
     * 1:奖励达到上限
     */
      @ApiEnumProperty("入账类型 1:奖励达到上限")
      LIMITED("奖励达到上限"),

    /**
     * 2:奖励未开启
     */
    @ApiEnumProperty("入账类型 2:奖励未开启")
      UNOPENED("奖励未开启");


    private String desc;

    FailReasonFlag(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public FailReasonFlag fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

    @WritingConverter
    public enum FailReasonFlagToIntegerConverter implements Converter<FailReasonFlag, Integer> {
        INSTANCE;
        @Override
        public Integer convert(FailReasonFlag source) {
            return source.ordinal();
        }
    }

    @ReadingConverter
    public enum IntegerToFailReasonFlagConverter implements Converter<Integer, FailReasonFlag> {
        INSTANCE;
        @Override
        public FailReasonFlag convert(Integer source) {
            for (FailReasonFlag item : FailReasonFlag.values()) {
                if (item.ordinal() == source) {
                    return item;
                }
            }
            return null;
        }
    }
}
