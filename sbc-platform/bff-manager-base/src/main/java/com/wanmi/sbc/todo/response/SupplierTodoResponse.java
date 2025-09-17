package com.wanmi.sbc.todo.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商家待处理面板
 * Created by daiyitian on 2017/9/18.
 */
@Schema
@Data
public class SupplierTodoResponse extends BasicResponse {

    /**
     * 待开票订单
     */
    @Schema(description = "待开票订单")
    private long waitInvoice;

    /**
     * 待审核商品
     */
    @Schema(description = "待审核商品")
    private long waitGoods;

    /**
     * 待结算账单
     */
    @Schema(description = "待结算账单")
    private long waitSettle;

    /**
     * 商品审核开关 true:开启 false:关闭
     */
    @Schema(description = "商品审核开关",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
    private boolean checkGoodsFlag = false;
}
