package com.wanmi.sbc.marketing.bean.enums;

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
 * @Author: songhanlin
 * @Date: Created In 10:43 AM 2018/9/12
 * @Description: 优惠券作用范围类型(0, 1, 2, 3)
 * 0 全部商品，        1 品牌，
 * 2 平台类目/店铺分类，3 自定义货品（店铺可用）
 */
@ApiEnum
public enum ScopeType {

    /**
     * 全部商品
     */
    @ApiEnumProperty("0：全部商品")
    ALL(0),

    /**
     * 品牌
     */
    @ApiEnumProperty("1：品牌")
    BRAND(1),

    /**
     * 平台(boss)类目
     */
    @ApiEnumProperty("2：平台(boss)类目")
    BOSS_CATE(2),

    /**
     * 店铺分类
     */
    @ApiEnumProperty("3：店铺分类")
    STORE_CATE(3),

    /**
     * 自定义货品（店铺可用）
     */
    @ApiEnumProperty("4：自定义货品（店铺可用）")
    SKU(4),

    /**
     * 门店分类
     */
    @ApiEnumProperty("5：门店分类")
    O2O_CATE(5),

    /**
     * 店铺
     */
    @ApiEnumProperty("6：店铺")
    STORE(6);

    private Integer value;

    ScopeType(Integer value){
        this.value = value;
    }

    private static Map<Integer, ScopeType> dataMap = new HashMap<>();

    static {
        Arrays.asList(ScopeType.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static ScopeType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum ScopeTypeToIntegerConverter implements Converter<ScopeType, Integer> {
        INSTANCE;
        @Override
        public Integer convert(ScopeType source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToScopeTypeConverter implements Converter<Integer, ScopeType> {
        INSTANCE;
        @Override
        public ScopeType convert(Integer source) {
            for (ScopeType item : ScopeType.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }

}
