package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Author: daiyitian
 * @Date: Created In 上午9:49 2019/3/1
 * @Description: 第三方平台类型
 */
@ApiEnum
public enum ThirdPlatformType {

    /**
     * linkedMall
     */
    @ApiEnumProperty("LINKED_MALL")
    LINKED_MALL(0),

    /**
     * 京东VOP
     */
    @ApiEnumProperty("VOP")
    VOP(1),

    /**
     * 微信视频号  不可用于订单 只用于类目映射类目属性
     */
    @ApiEnumProperty("WECHAT_VIDEO")
    WECHAT_VIDEO(2);

    private Integer value;

    ThirdPlatformType(Integer value){
        this.value = value;
    }

    private static Map<Integer, ThirdPlatformType> dataMap = new HashMap<>();

    static {
        Arrays.asList(ThirdPlatformType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static ThirdPlatformType fromValue(Integer value) {
        if(value == null) {
            return null;
        }
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum ThirdPlatformTypeToIntegerConverter implements Converter<ThirdPlatformType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(ThirdPlatformType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToThirdPlatformTypeConverter implements Converter<Integer, ThirdPlatformType> {
        INSTANCE;
        @Override
        public ThirdPlatformType convert(Integer source) {
            for (ThirdPlatformType item : ThirdPlatformType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }

}
