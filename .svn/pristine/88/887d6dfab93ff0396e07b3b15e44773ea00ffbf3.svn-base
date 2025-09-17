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
 * 分销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销
 * Created by CHENLI
 */
@ApiEnum
public enum DistributionGoodsAudit {
    @ApiEnumProperty("0：普通商品")
    COMMON_GOODS(0),

    @ApiEnumProperty("1：待审核")
    WAIT_CHECK(1),

    @ApiEnumProperty("2：已审核通过")
    CHECKED(2),

    @ApiEnumProperty("3：审核不通过")
    NOT_PASS(3),

    @ApiEnumProperty("4：禁止分销")
    FORBID(4);

    private Integer value;

    DistributionGoodsAudit(Integer value){
        this.value = value;
    }

    private static Map<Integer, DistributionGoodsAudit> dataMap = new HashMap<>();

    static {
        Arrays.asList(DistributionGoodsAudit.values()).forEach(
                t -> dataMap.put(t.getValue(), t)
        );
    }

    private Integer getValue() {
        return this.value;
    }

    @JsonCreator
    public static DistributionGoodsAudit fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.getValue();
    }

    @WritingConverter
    public enum DistributionGoodsAuditToIntegerConverter implements Converter<DistributionGoodsAudit, Integer> {
        INSTANCE;
        @Override
        public Integer convert(DistributionGoodsAudit source) {
            return source.getValue();
        }
    }

    @ReadingConverter
    public enum IntegerToDistributionGoodsAuditConverter implements Converter<Integer, DistributionGoodsAudit> {
        INSTANCE;
        @Override
        public DistributionGoodsAudit convert(Integer source) {
            for (DistributionGoodsAudit item : DistributionGoodsAudit.values()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            return null;
        }
    }
}
