package com.wanmi.sbc.order.returnorder.service;

import com.wanmi.sbc.order.returnorder.model.entity.ReturnItem;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.root.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


/**
 * @author He
 * @description <p>负责订单跨境退单的处理方法切口 无具体实现
 */
@Slf4j
@Service
public class
ReturnTradeIncision {


    /**
     *  退单的退单类型
     * @param trade
     * @param returnOrder
     */
    public void setCrossReturnOrderType(Trade trade, ReturnOrder returnOrder) { }

    /**
     *  填充退单商品扩展信息
     * @param returnItem
     * @param item
     */
    public void setReturnItemExpand(ReturnItem returnItem, TradeItem item) { }

    /**
     *  计算可退金额
     * @param totalPrice
     * @param trade
     * @return
     */
    public BigDecimal calcCanReturnPrice(BigDecimal totalPrice, Trade trade) {
        return totalPrice;
    }

    /**
     * C端发起部分退货退款，处理并校验可退商品
     * @param trade
     * @return
     */
    public Trade handleCanReturnItems(Trade trade) {
        return trade;
    }
}
