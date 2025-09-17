package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import static com.wanmi.sbc.common.sensitiveword.SensitiveWordsSignUtil.*;

/**
 * @description 脱敏类型
 * @author wur
 * @date: 2022/7/11 14:00
 */
@ApiEnum
public enum SignWordType {

    /** */
    @ApiEnumProperty("OBJECT")
    OBJECT("OBJECT") {
        @Override
        public String process(String word) {
            return null;
        }
    },

    /** */
    @ApiEnumProperty("LIST")
    LIST("LIST") {
        @Override
        public String process(String word) {
            return null;
        }
    },

    /** */
    @ApiEnumProperty("PHONE")
    PHONE("PHONE") {
        @Override
        public String process(String word) {
            return isPhone(word);
        }
    },

    /** */
    @ApiEnumProperty("ADDRESS")
    ADDRESS("ADDRESS") {
        @Override
        public String process(String word) {
            return isAddress(word);
        }
    },

    /** */
    @ApiEnumProperty("NAME")
    NAME("NAME") {
        @Override
        public String process(String word) {
            return isName(word);
        }
    },

    /** */
    @ApiEnumProperty("IMG")
    IMG("IMG") {
        @Override
        public String process(String word) {
            return null;
        }
    },

    /** */
    @ApiEnumProperty("EMAIL")
    EMAIL("EMAIL") {
        @Override
        public String process(String word) {
            return isEmail(word);
        }
    },

    /** */
    @ApiEnumProperty("IDCARD")
    IDCARD("IDCARD") {
        @Override
        public String process(String word) {
            return isIdcard(word);
        }
    },

    /** */
    @ApiEnumProperty("BANKNO")
    BANKNO("BANKNO") {
        @Override
        public String process(String word) {
            return isBackNo(word);
        }
    };

    private final String value;

    SignWordType(String value) {
        this.value = value;
    }

    @JsonValue
    public String toValue() {
        return value;
    }

    public abstract String process(String word);
}
