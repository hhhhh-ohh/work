package com.wanmi.sbc.empower.provider.impl.sellplatform.order;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.sellplatform.order.PlatformOrderProvider;
import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.order.*;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformAddOrderResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformCompanyResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformOrderPayParamResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.order.PlatformQueryOrderResponse;
import com.wanmi.sbc.empower.sellplatform.PlatformContext;
import com.wanmi.sbc.empower.sellplatform.PlatformOrderService;
import com.wanmi.sbc.empower.sellplatform.PlatformServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author wur
 * @className ThirdPlatformOrderController
 * @description 生成订单处理
 * @date 2022/4/12 18:05
 **/
@RestController
public class PlatformOrderController implements PlatformOrderProvider {

    @Autowired private PlatformContext thirdPlatformContext;

    @Override
    public BaseResponse<PlatformAddOrderResponse> addOrder(@Valid PlatformAddOrderRequest request) {
        PlatformOrderService orderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.ORDER_SERVICE);
        return orderService.addOrder(request);
    }

    @Override
    public BaseResponse<PlatformOrderPayParamResponse> getPayParams(PlatformOrderRequest request) {
        PlatformOrderService orderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.ORDER_SERVICE);
        return orderService.getOrderPayParam(request);
    }

    @Override
    public BaseResponse pay(@Valid PlatformOrderPayRequest request) {
        PlatformOrderService orderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.ORDER_SERVICE);
        return orderService.pay(request);
    }

    @Override
    public BaseResponse deliverySend(@Valid PlatformDeliverySendRequest request) {
        PlatformOrderService orderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.ORDER_SERVICE);
        return orderService.deliverySend(request);
    }

    @Override
    public BaseResponse deliveryRecieve(@Valid PlatformOrderRequest request) {
        PlatformOrderService orderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.ORDER_SERVICE);
        return orderService.deliveryRecieve(request);
    }

    @Override
    public BaseResponse<PlatformQueryOrderResponse> queryOrder(@Valid PlatformOrderRequest request) {
        PlatformOrderService orderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.ORDER_SERVICE);
        return orderService.getOrder(request);
    }

    @Override
    public BaseResponse closeOrder(@Valid PlatformOrderRequest request) {
        PlatformOrderService orderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.ORDER_SERVICE);
        return orderService.closeOrder(request);
    }

    @Override
    public BaseResponse<PlatformCompanyResponse> getCompanyList(ThirdBaseRequest request) {
        PlatformOrderService orderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.ORDER_SERVICE);
        return orderService.getCompanyList();
    }
}
