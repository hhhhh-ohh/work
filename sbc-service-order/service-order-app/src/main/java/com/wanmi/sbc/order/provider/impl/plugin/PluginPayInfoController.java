package com.wanmi.sbc.order.provider.impl.plugin;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.order.api.provider.plugin.PluginPayInfoProvider;
import com.wanmi.sbc.order.api.request.plugin.PluginPayInfoAddRequest;
import com.wanmi.sbc.order.api.request.plugin.PluginPayInfoModifyRequest;
import com.wanmi.sbc.order.plugin.PluginOrderServiceFactory;
import com.wanmi.sbc.order.plugin.PluginPayInfoBaseService;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Objects;


@Validated
@RestController
public class PluginPayInfoController implements PluginPayInfoProvider {

    @Autowired private PluginOrderServiceFactory pluginOrderServiceFactory;

    @Autowired private TradeService tradeService;

    @Override
    public BaseResponse add(@Valid PluginPayInfoAddRequest request) {
        //根据ID查询订单信息
        Trade trade = tradeService.detail(request.getOrderCode());
        if (Objects.isNull(trade)) {
            return BaseResponse.SUCCESSFUL();
        }
        //验证订单是否是跨境订单
        if (Objects.nonNull(trade.getCrossBorderFlag()) && trade.getCrossBorderFlag()) {
            pluginOrderServiceFactory.getPluginService(PluginPayInfoBaseService.class, PluginType.CROSS_BORDER).add(request);
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modify(@Valid PluginPayInfoModifyRequest request) {
        //根据ID查询订单信息
        Trade trade = tradeService.detail(request.getOrderCode());
        if (Objects.isNull(trade)) {
            return BaseResponse.SUCCESSFUL();
        }
        //验证订单是否是跨境订单
        if (Objects.nonNull(trade.getCrossBorderFlag()) && trade.getCrossBorderFlag()) {
            pluginOrderServiceFactory.getPluginService(PluginPayInfoBaseService.class, PluginType.CROSS_BORDER).modify(request);
        }
        return BaseResponse.SUCCESSFUL();
    }
}
