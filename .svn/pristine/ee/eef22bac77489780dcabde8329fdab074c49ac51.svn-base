package com.wanmi.sbc.vas.provider.impl.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformOrderProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.order.SellPlatformAddOrderRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.order.SellPlatformDeliverySendRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.order.SellPlatformOrderRequest;
import com.wanmi.sbc.vas.api.response.sellplatform.order.SellPlatformExpressListResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.order.SellPlatformOrderResponse;
import com.wanmi.sbc.vas.api.response.sellplatform.order.SellPlatformQueryOrderResponse;
import com.wanmi.sbc.vas.sellplatform.SellPlatformContext;
import com.wanmi.sbc.vas.sellplatform.SellPlatformOrderService;
import com.wanmi.sbc.vas.sellplatform.SellPlatformServiceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * @author wur
 * @className SellPlatformOrderController
 * @description TODO
 * @date 2022/4/20 13:42
 **/
@Slf4j
@RestController
public class SellPlatformOrderController implements SellPlatformOrderProvider {

    @Autowired
    private SellPlatformContext sellPlatformContext;

    @Override
    public BaseResponse<SellPlatformOrderResponse> addOrder(@Valid SellPlatformAddOrderRequest request) {
        SellPlatformOrderService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_ORDER_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.addOrder(request);
    }

    @Override
    public BaseResponse<SellPlatformQueryOrderResponse> queryOrder(@Valid SellPlatformOrderRequest request) {
        SellPlatformOrderService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_ORDER_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.queryOrder(request);
    }

    @Override
    public BaseResponse cancelOrder(@Valid SellPlatformOrderRequest request) {
        SellPlatformOrderService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_ORDER_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.cancelOrder(request);
    }

    @Override
    public BaseResponse deliverySend(SellPlatformDeliverySendRequest request) {
        SellPlatformOrderService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_ORDER_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.deliverySend(request);
    }

    @Override
    public BaseResponse deliveryReceive(@Valid SellPlatformOrderRequest request) {
        SellPlatformOrderService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_ORDER_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.deliveryReceive(request);
    }

    @Override
    public BaseResponse<SellPlatformExpressListResponse> getExpressList(@Valid SellPlatformBaseRequest request) {
        SellPlatformOrderService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_ORDER_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.getExpressList();
    }
}