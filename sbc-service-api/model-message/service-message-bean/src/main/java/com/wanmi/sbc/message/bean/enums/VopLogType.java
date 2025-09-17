package com.wanmi.sbc.message.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @Author xufeng
 * @Description Vop日志类型枚举类
 * @Date 14:28 2022/05/20
 * @Param
 * @return
 **/
@ApiEnum
public enum VopLogType {
    //1:拆单,2:价格变动 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更

    @ApiEnumProperty(" 0：")
    Zero,
    @ApiEnumProperty(" 1：拆单")
    One,
    @ApiEnumProperty(" 2：价格变动")
    Two,
    @ApiEnumProperty(" 3：订单取消")
    Three,
    @ApiEnumProperty(" 4：商品上下架变更")
    Four,
    @ApiEnumProperty(" 5：")
    Five,
    @ApiEnumProperty(" 6：商品池内商品添加/删除")
    Six,
    @ApiEnumProperty(" 7：")
    Seven,
    @ApiEnumProperty(" 8：")
    Eight,
    @ApiEnumProperty(" 9：")
    Nine,
    @ApiEnumProperty(" 10：订单取消")
    Ten,
    @ApiEnumProperty(" 11：")
    Eleven,
    @ApiEnumProperty(" 12：配送单生成成功")
    Twelve,
    @ApiEnumProperty(" 13：")
    Thirteen,
    @ApiEnumProperty(" 14：支付失败消息")
    Fourteen,
    @ApiEnumProperty(" 15：")
    Fifteen,
    @ApiEnumProperty(" 16：商品信息变更")
    Sixteen;
    @JsonCreator
    public VopLogType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
