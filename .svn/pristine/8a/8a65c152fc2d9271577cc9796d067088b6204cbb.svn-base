package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 营销商品状态
 * @author  qiyuanzhao
 * @date 2022/10/08 10:10
 **/
@ApiEnum
public enum MarketingGoodsStatus {

    /**
     * 无状态
     */
    @ApiEnumProperty("0:无状态")
    NOTHING,

    /**
     * 供应商已删除
     */
    @ApiEnumProperty("1:供应商已删除")
    PROVIDER_DELETED,

    /**
     * 供应商已下架
     */
    @ApiEnumProperty("2:供应商已下架")
    PROVIDER_REMOVE,

    /**
     * 渠道商品已被平台禁售
     */
    @ApiEnumProperty("3:渠道商品已被平台禁售")
    CHANNELGOODS_BOSS_CLOSED,

    /**
     * 商品已被平台禁售
     */
    @ApiEnumProperty("4:商品已被平台禁售")
    BOSS_CLOSED,

    /**
     * 商品已被删除
     */
    @ApiEnumProperty("5:商品已被删除")
    DELETE,

    /**
     * 商品已下架
     */
    @ApiEnumProperty("6:商品已下架")
    REMOVE,

    /**
     * vop已下架
     */
    @ApiEnumProperty("7:vop已下架")
    VOP_REMOVE,

    /**
     * LM已下架
     */
    @ApiEnumProperty("8:LM已下架")
    LM_REMOVE,

    /**
     * 其他
     */
    @ApiEnumProperty("9:其他")
    OTHER;


    @JsonCreator
    public static MarketingGoodsStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public Integer toValue() {
        return this.ordinal();
    }
}
