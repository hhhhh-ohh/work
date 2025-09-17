package com.wanmi.sbc.order.trade.service;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrderListResponse;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.TradePrice;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.request.TradeParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author He
 * @description <p>负责订单下单跨境的处理方法 无具体实现
 */
@Slf4j
@Service
public class TradeCommitIncision {


    /**
     * 校验订单是否符合规则
     * @param tradeItemGroups
     */
    public void checkTradeRealName(List<TradeItemGroup> tradeItemGroups, TradeCommitRequest tradeCommitRequest) {}
    /**
     * 校验跨境信息的商品信息是否被删除
     * @param trades
     */
    public void checkTradeSkuType(List<Trade> trades) {  }

    /**
     * 处理电子口岸信息 验证收货人身份认证购买人身份证 0元订单等
     * @param trades
     * @param tradeCommitRequest
     */
    public void checkTradeData(List<Trade> trades, TradeCommitRequest tradeCommitRequest) { }

    /**
     *  填充跨境标识和商品验证
     * @param trade
     * @param tradeParams
     */
    public void setTradePluginType(Trade trade, TradeParams tradeParams) {}

    /**
     *  计算营销后 税费信息
     * @param trade
     */
    public void calcTradeItemTax(Trade trade) {}

    /**
     *  订单商品 填充类型
     * @param tradeItem
     * @param goodsInfo
     */
    public void tradeItemPluginType(TradeItem tradeItem, GoodsInfoVO goodsInfo) {}

    /**
     * 跨境的运费计算
     * @param deliveryPrice
     * @param tradePrice
     * @return
     */
    public BigDecimal calcDeliveryPrice(BigDecimal deliveryPrice, TradePrice tradePrice,Trade trade) {
        return deliveryPrice;
    }

    /**
     * 计算跨境订单总支付金额
     * @param trades
     * @return
     */
    public List<Trade> calcTotalPrice(List<Trade> trades) {
        return trades;
    }

    /**
     *  跨境订单不需要审核
     * @param trade
     */
    public void tradeAuditState(Trade trade) { }

    /**
     *  下单返回加入跨境标识
     * @param result
     * @return
     */
    public boolean resultPluginType(Trade result) {
        return false;
    }

    /**
     *  拆单
     */
    public void splitOrder(Trade trade) {}

    /**
     * 支付单查询
     * @param tradeList
     * @return
     */
    public FindPayOrderListResponse findPayOrderList(List<Trade> tradeList){return FindPayOrderListResponse.builder().build();}

    /**
     * 支付单查询
     * @param orderNo
     * @return
     */
    public Long findPayOrder(String orderNo){return null;}
}
