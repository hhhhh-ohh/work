package com.wanmi.sbc.empower.provider.impl.sellplatform.returnorder;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.sellplatform.returnorder.PlatformReturnOrderProvider;
import com.wanmi.sbc.empower.api.request.sellplatform.returnorder.*;
import com.wanmi.sbc.empower.api.response.sellplatform.returnorder.PlatformGetReturnOrderResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.returnorder.PlatformAddReturnOrderResponse;
import com.wanmi.sbc.empower.sellplatform.PlatformContext;
import com.wanmi.sbc.empower.sellplatform.PlatformReturnOrderService;
import com.wanmi.sbc.empower.sellplatform.PlatformServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author wur
 * @className PlatformReturnOrderController
 * @description 生成售后单处理
 * @date 2022/4/12 18:05
 **/
@RestController
public class PlatformReturnOrderController implements PlatformReturnOrderProvider {


    @Autowired private PlatformContext thirdPlatformContext;

    @Override
    public BaseResponse updateAccountAddress(PlatformUpdateShopAddressRequest request) {
        PlatformReturnOrderService returnOrderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.RETURN_ORDER_SERVICE);
        return returnOrderService.updateAccountAddress(request);
    }

    @Override
    public BaseResponse<PlatformAddReturnOrderResponse> addReturnOrder(@Valid PlatformAddReturnOrderRequest request) {
        PlatformReturnOrderService returnOrderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.RETURN_ORDER_SERVICE);
        return returnOrderService.addReturnOrder(request);
    }

    @Override
    public BaseResponse cancelReturnOrder(@Valid PlatformReturnOrderRequest request) {
        PlatformReturnOrderService returnOrderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.RETURN_ORDER_SERVICE);
        return returnOrderService.cancelReturnOrder(request);
    }

    @Override
    public BaseResponse acceptReturn(@Valid PlatformAcceptReturnRequest request) {
        PlatformReturnOrderService returnOrderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.RETURN_ORDER_SERVICE);
        return returnOrderService.acceptReturn(request);
    }

    @Override
    public BaseResponse upReturnInfo(@Valid PlatformUpReturnInfoRequest request) {
        PlatformReturnOrderService returnOrderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.RETURN_ORDER_SERVICE);
        return returnOrderService.upReturnInfo(request);
    }

    @Override
    public BaseResponse acceptRefund(@Valid PlatformReturnOrderRequest request) {
        PlatformReturnOrderService returnOrderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.RETURN_ORDER_SERVICE);
        return returnOrderService.acceptRefund(request);
    }

    @Override
    public BaseResponse rejectReturn(@Valid PlatformReturnOrderRequest request) {
        PlatformReturnOrderService returnOrderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.RETURN_ORDER_SERVICE);
        return returnOrderService.rejectReturn(request);
    }

    @Override
    public BaseResponse upReturnCertificates(@Valid PlatformUpReturnCertificatesRequest request) {
        PlatformReturnOrderService returnOrderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.RETURN_ORDER_SERVICE);
        return returnOrderService.upReturnCertificates(request);
    }

    @Override
    public BaseResponse<PlatformGetReturnOrderResponse> getReturnOrder(PlatformReturnOrderRequest request) {
        PlatformReturnOrderService returnOrderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.RETURN_ORDER_SERVICE);
        return returnOrderService.getReturnOrder(request);
    }

    @Override
    public BaseResponse updateReturn(PlatformUpdateReturnOrderRequest request) {
        PlatformReturnOrderService returnOrderService =
                thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.RETURN_ORDER_SERVICE);
        return returnOrderService.updateReturnOrder(request);
    }
}
