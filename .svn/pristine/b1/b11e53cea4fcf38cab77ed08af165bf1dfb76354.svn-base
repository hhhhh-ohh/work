package com.wanmi.sbc.order.trade.service;

import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.root.TradeConfirmItem;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;

import java.util.List;

/***
 * 订单service接口
 * @className TradeServiceInterface
 * @author zhengyang
 * @date 2021/11/12 13:54
 **/
public interface TradeServiceInterface {

    /**
     * 根据快照封装订单确认页信息
     *
     * @param g      订单商品快照
     * @param gifts  订单赠品
     * @return       订单确认对象
     */
    TradeConfirmItem getPurchaseInfo(TradeItemGroup g, List<TradeItem> gifts);
}
