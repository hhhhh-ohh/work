package com.wanmi.sbc.goods.bean.enums;

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
 * 商品状态 0：正常 1：缺货 2：失效
 * Created by Daiyitian on 2017/4/13.
 */
@ApiEnum
public enum GoodsStatus {

    @ApiEnumProperty(" 0：正常")
    OK(0),

    @ApiEnumProperty("1：缺货")
    OUT_STOCK(1),

    @ApiEnumProperty("2：失效")
    INVALID(2),

    @ApiEnumProperty("3：无购买权限")
    NO_AUTH(3),

    @ApiEnumProperty("4：不支持销售")
    NO_SALE(4);

    private Integer value;

    GoodsStatus(Integer value){
        this.value = value;
    }

    private static Map<Integer, GoodsStatus> dataMap = new HashMap<>();

    static {
        Arrays.asList(GoodsStatus.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static GoodsStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum GoodsStatusToIntegerConverter implements Converter<GoodsStatus, Integer> {
        INSTANCE;
        @Override
        public Integer convert(GoodsStatus source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToGoodsStatusConverter implements Converter<Integer, GoodsStatus> {
        INSTANCE;
        @Override
        public GoodsStatus convert(Integer source) {
            for (GoodsStatus item : GoodsStatus.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
