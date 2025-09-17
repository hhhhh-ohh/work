package com.wanmi.sbc.marketing.bean.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import com.wanmi.sbc.common.enums.DefaultFlag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

/***
 * O2O-BOSS端新增活动参与门店
 * @className ParticipateType
 * @author zhengyang
 * @date 2021/10/11 10:54
 **/
@ApiEnum
public enum ParticipateType {

    /**
     * 全部门店均参与该活动
     */
    @ApiEnumProperty("全部")
    ALL("全部门店"),

    /**
     * 部门门店参与该活动
     * 参与门店
     */
    @ApiEnumProperty("部分")
    PART("部分门店");

    private String type;

    ParticipateType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    @JsonCreator
    public static ParticipateType fromValue(int value) {
        return ParticipateType.values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }


    @WritingConverter
    public enum ParticipateTypeToIntegerConverter implements Converter<ParticipateType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(ParticipateType source) {
            return source.toValue();
        }
    }

    @ReadingConverter
    public enum IntegerToParticipateTypeConverter implements Converter<Integer, ParticipateType> {
        INSTANCE;
        @Override
        public ParticipateType convert(Integer source) {
            for (ParticipateType item : ParticipateType.values()) {
                if (item.toValue() == source) {
                    return item;
                }
            }
            return null;
        }
    }
}
