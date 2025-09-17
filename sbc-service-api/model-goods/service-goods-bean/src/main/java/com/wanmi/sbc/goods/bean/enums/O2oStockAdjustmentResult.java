package com.wanmi.sbc.goods.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/***
 * 批量库存调整执行结果
 * 0 未执行、1 执行成功、2 执行失败
 * @className O2oStockAdjustmentResult
 * @author zhengyang
 * @date 2021/8/11 11:05
 **/
@ApiEnum
public enum O2oStockAdjustmentResult {

    /***
     * 库存调整未执行
     */
    @ApiEnumProperty("0 未执行")
    UNDO,

    /***
     * 库存调整执行成功
     */
    @ApiEnumProperty("1 执行成功")
    DONE,

    /***
     * 库存调整执行失败
     */
    @ApiEnumProperty("2 执行失败")
    FAIL;

    @JsonCreator
    public static O2oStockAdjustmentResult fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
