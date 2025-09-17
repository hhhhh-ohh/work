package com.wanmi.sbc.order.service;


import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeDeliverRequest;
import com.wanmi.sbc.order.api.request.trade.TradeDeliveryCheckRequest;
import com.wanmi.sbc.order.bean.dto.ShippingItemDTO;
import com.wanmi.sbc.order.bean.dto.TradeDeliverDTO;
import com.wanmi.sbc.order.bean.dto.TradeDeliverRequestDTO;
import com.wanmi.sbc.order.bean.enums.ShipperType;
import com.wanmi.sbc.order.bean.vo.TradeDeliverVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 社区团购服务
 */
@Service
public class CommunityTradeService {

    @Autowired private TradeProvider tradeProvider;

    @Autowired private TradeQueryProvider tradeQueryProvider;

    @Autowired CommonUtil commonUtil;

    /**
     * 通知取货
     * @param tradeVO 订单数据
     * @return 发货id
     */
    @GlobalTransactional
    public String deliver(TradeVO tradeVO){
        String tid = tradeVO.getId();
        TradeDeliverRequestDTO tradeDeliverRequest = new TradeDeliverRequestDTO();
        List<ShippingItemDTO> shippingItemList = new ArrayList<>();
        tradeVO.getTradeItems().forEach(i -> {
            shippingItemList.add(ShippingItemDTO.builder()
                    .goodsType(0)
                    .itemName(i.getSkuName())
                    .itemNum(i.getNum())
                    .skuId(i.getSkuId())
                    .skuNo(i.getSkuNo())
                    .build());
        });
        tradeDeliverRequest.setShippingItemList(shippingItemList);
        TradeDeliveryCheckRequest tradeDeliveryCheckRequest = TradeDeliveryCheckRequest.builder()
                .tid(tid)
                .tradeDeliver(tradeDeliverRequest)
                .build();
        tradeDeliverRequest.setDeliverTime(DateUtil.nowDate());
        tradeQueryProvider.deliveryCheck(tradeDeliveryCheckRequest);
        //发货校验
        ExpressCompanyVO expressCompanyVO = null;
        TradeDeliverVO tradeDeliver = tradeDeliverRequest.toTradeDevlier(expressCompanyVO);
        tradeDeliver.setShipperType(ShipperType.LEADER);

        TradeDeliverRequest tradeDeliverRequest1 = TradeDeliverRequest.builder()
                .tradeDeliver(KsBeanUtil.convert(tradeDeliver, TradeDeliverDTO.class))
                .tid(tid)
                .operator(commonUtil.getOperator())
                .build();
        return tradeProvider.deliver(tradeDeliverRequest1).getContext().getDeliverId();
    }
}
