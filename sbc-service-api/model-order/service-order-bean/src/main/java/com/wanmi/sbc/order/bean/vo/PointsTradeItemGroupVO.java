package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>积分订单项</p>
 * Created by yinxianzhi on 2019-05-20-下午6:12.
 */
@Data
@Schema
public class PointsTradeItemGroupVO extends BasicResponse {
    /**
     * 订单商品sku
     */
    @Schema(description = "订单商品sku")
    private TradeItemVO tradeItem;

    /**
     * 商家与店铺信息
     */
    @Schema(description = "商家与店铺信息")
    private SupplierVO supplier;
}
