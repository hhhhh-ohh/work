package com.wanmi.sbc.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;import org.springframework.core.convert.converter.Converter;import org.springframework.data.convert.ReadingConverter;import org.springframework.data.convert.WritingConverter;

/**
 * @author zhanggaolei
 * @type InvoiceStyle.java
 * @desc 发票样式
 * @date 2023/3/10 09:36
 */
@ApiEnum
public enum InvoiceStyle {
    @ApiEnumProperty("增值税专用发票")
    SPECIAL,

    @ApiEnumProperty("个人发票")
    INDIVIDUAL,

    @ApiEnumProperty("公司发票")
    COMPANY;

    @JsonCreator
    public static InvoiceStyle fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }


    @WritingConverter
    public enum InvoiceStyleToIntegerConverter implements Converter<InvoiceStyle, Integer> {
        INSTANCE;
        @Override
        public Integer convert(InvoiceStyle source) {
            return source.ordinal();
        }
    }

    @ReadingConverter
    public enum IntegerToInvoiceStyleConverter implements Converter<Integer, InvoiceStyle> {
        INSTANCE;
        @Override
        public InvoiceStyle convert(Integer source) {
            for (InvoiceStyle item : InvoiceStyle.values()) {
                if (item.ordinal() == source) {
                    return item;
                }
            }
            return null;
        }
    }
}
