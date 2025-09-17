package com.wanmi.sbc.dw.bean.enums;

import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.HashSet;
import java.util.Set;

@Schema
public enum DimensionType {

    @ApiEnumProperty("terminal_source: 终端")
    TERMINAL_SOURCE("终端类型"),

    @ApiEnumProperty("num:次数")
    NUM("次数"),

    @ApiEnumProperty("day_num:天数")
    DAY_NUM("天数"),

    @ApiEnumProperty("to_date_num:距离当天的天数")
    TO_DATE_NUM("距离当天的天数"),

    @ApiEnumProperty("money:金额")
    MONEY("金额"),

    @ApiEnumProperty("time:时间")
    TIME("时间"),

    @ApiEnumProperty("date:日期")
    DATE("日期"),

    @ApiEnumProperty("cate_top_id:商品类目")
    CATE_TOP_ID("商品类目"),

    @ApiEnumProperty("cate_id:商品品类")
    CATE_ID("商品品类"),

    @ApiEnumProperty("brand_id:商品品牌")
    BRAND_ID("商品品牌"),

    @ApiEnumProperty("goods_id:商品")
    GOODS_ID("商品"),

    @ApiEnumProperty("store_id:店铺")
    STORE_ID("店铺"),

    @ApiEnumProperty("share_goods_sale_num:分享赚销售额")
    SHARE_GOODS_SALE_NUM("分享赚销售额"),

    @ApiEnumProperty("share_goods_commission_num:分享赚佣金收入")
    SHARE_GOODS_COMMISSION_NUM("分享赚佣金收入"),

    @ApiEnumProperty("invite_new_num:邀新人数")
    INVITE_NEW_NUM("邀新人数"),

    @ApiEnumProperty("effective_invite_new_num:有效邀新人数")
    EFFECTIVE_INVITE_NEW_NUM("有效邀新人数"),

    @ApiEnumProperty("invite_new_reward:邀新奖励")
    INVITE_NEW_REWARD("邀新奖励");

    private final String value;

    DimensionType(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }




    /**
     * 需要进行id查询转化的类型
     * @return
     */
    public static Set<String> needReplaceType(){
        Set<String> set = new HashSet();
        set.add(STORE_ID.name());
        set.add(GOODS_ID.name());
        set.add(CATE_ID.name());
        set.add(CATE_TOP_ID.name());
        set.add(BRAND_ID.name());
        return set;
    }
}