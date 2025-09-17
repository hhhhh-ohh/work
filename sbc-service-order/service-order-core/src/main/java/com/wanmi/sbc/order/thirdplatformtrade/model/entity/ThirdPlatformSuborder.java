package com.wanmi.sbc.order.thirdplatformtrade.model.entity;

import com.wanmi.sbc.order.trade.model.entity.TradeState;
import com.wanmi.sbc.order.trade.model.entity.value.TradePrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 第三方平台子订单
 * @author  wur
 * @date: 2021/5/18 13:49
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThirdPlatformSuborder {

    /**
     * 订单号
     */
    private String suborderId;

    /**
     * 订单状态
     */
    private TradeState tradeState;

    /**
     * 订单金额
     */
    private TradePrice tradePrice;

    /**
     * 商品信息
     */
    private List<ThirdPlatformSuborderItem>  itemList= new ArrayList<>();
}
