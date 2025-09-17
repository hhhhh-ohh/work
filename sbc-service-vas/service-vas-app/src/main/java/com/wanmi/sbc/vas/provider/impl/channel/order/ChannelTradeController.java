package com.wanmi.sbc.vas.provider.impl.channel.order;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.provider.channel.order.ChannelTradeProvider;
import com.wanmi.sbc.vas.api.request.channel.ChannelOrderCompensateRequest;
import com.wanmi.sbc.vas.api.request.channel.ChannelOrderFreightRequest;
import com.wanmi.sbc.vas.api.request.channel.ChannelOrderSplitRequest;
import com.wanmi.sbc.vas.api.request.channel.ChannelOrderVerifyRequest;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderCompensateResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderFreightResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderSplitResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderVerifyResponse;
import com.wanmi.sbc.vas.channel.order.ChannelOrderService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * @description 渠道订单服务实现
 * @author daiyitian
 * @date 2021/5/21 21:45
 */
@RestController
public class ChannelTradeController implements ChannelTradeProvider {
    @Autowired private List<ChannelOrderService> channelOrderServiceList;

    @Override
    public BaseResponse<ChannelOrderVerifyResponse> verifyOrder(
            @RequestBody @Valid ChannelOrderVerifyRequest request) {
        ChannelOrderVerifyResponse response = new ChannelOrderVerifyResponse();
        if(CollectionUtils.isEmpty(channelOrderServiceList)){
            return BaseResponse.success(response);
        }
        channelOrderServiceList.forEach(
                i -> {
                    ChannelOrderVerifyResponse tmpResponse = i.verifyOrder(request.getOrderList());
                    response.getOffAddedSkuId().addAll(tmpResponse.getOffAddedSkuId());
                    if (tmpResponse.getInvalidGoods()) {
                        response.setInvalidGoods(tmpResponse.getInvalidGoods());
                    }

                    if (tmpResponse.getNoAuthGoods()) {
                        response.setNoAuthGoods(tmpResponse.getNoAuthGoods());
                    }

                    if (tmpResponse.getNoOutStockGoods()) {
                        response.setNoOutStockGoods(tmpResponse.getNoOutStockGoods());
                    }
                });
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<ChannelOrderSplitResponse> splitOrder(
            @RequestBody @Valid ChannelOrderSplitRequest request) {
        ChannelOrderSplitResponse response = new ChannelOrderSplitResponse();
        if(CollectionUtils.isEmpty(channelOrderServiceList)){
            return BaseResponse.success(response);
        }
        channelOrderServiceList.forEach(
                i -> {
                    ChannelOrderSplitResponse tmpResponse = i.splitOrder(request.getOrder());
                    response.getChannelTradeList().addAll(tmpResponse.getChannelTradeList());
                });
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<ChannelOrderCompensateResponse> compensateOrder(
            @RequestBody @Valid ChannelOrderCompensateRequest request) {
        ChannelOrderCompensateResponse response = new ChannelOrderCompensateResponse();
        if(CollectionUtils.isEmpty(channelOrderServiceList)){
            return BaseResponse.success(response);
        }
        channelOrderServiceList.forEach(
                i -> {
                    ChannelOrderCompensateResponse tmpResponse =
                            i.compensateOrder(request.getOrderList());
                    response.getUnconfirmedProviderTradeIds()
                            .addAll(tmpResponse.getUnconfirmedProviderTradeIds());
                    response.getUnconfirmedThirdTradeId()
                            .addAll(tmpResponse.getUnconfirmedThirdTradeId());
                    response.getPayErrProviderTradeIds()
                            .addAll(tmpResponse.getPayErrProviderTradeIds());
                    response.getPayErrThirdTradeId().addAll(tmpResponse.getPayErrThirdTradeId());
                    response.getPaySuccessThirdTradeId()
                            .addAll(tmpResponse.getPaySuccessThirdTradeId());
                });
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<ChannelOrderFreightResponse> queryFreight(@RequestBody @Valid ChannelOrderFreightRequest request) {
        ChannelOrderFreightResponse response = new ChannelOrderFreightResponse();
        BigDecimal sumFreight = BigDecimal.ZERO;
        for (ChannelOrderService service : channelOrderServiceList) {
            BigDecimal freight = service.queryFreight(request.getGoodsInfoList(), request.getAddress());
            sumFreight = sumFreight.add(freight);
        }
        response.setFreight(sumFreight);
        return BaseResponse.success(response);
    }
}
