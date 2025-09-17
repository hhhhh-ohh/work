package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 第三方平台子订单
 * @author  wur
 * @date: 2021/5/18 11:36
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradePlatformSuborderVO extends BasicResponse {

    /**
     * 操作员
     */
    @Schema(description = "订单号")
    private String suborderId;

    @Schema(description = "订单状态")
    private TradeStateVO tradeState;

    @Schema(description = "订单金额")
    private TradePriceVO tradePrice;

    @Schema(description = "商品信息")
    private List<TradePlatformSuborderItemVO>  itemList;

}
