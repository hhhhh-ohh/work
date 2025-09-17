package com.wanmi.sbc.vas.provider.impl.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformReturnOrderProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.returnorder.*;
import com.wanmi.sbc.vas.sellplatform.SellPlatformContext;
import com.wanmi.sbc.vas.sellplatform.SellPlatformReturnOrderService;
import com.wanmi.sbc.vas.sellplatform.SellPlatformServiceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * @author wur
 * @className SellPlatformReturnOrderController
 * @description TODO
 * @date 2022/4/20 13:42
 **/
@Slf4j
@RestController
public class SellPlatformReturnOrderController implements SellPlatformReturnOrderProvider {

    @Autowired
    private SellPlatformContext sellPlatformContext;

    @Override
    public BaseResponse updateAccountAddress(@Valid SellPlatformUpdateAccountAddressRequest request) {
        SellPlatformReturnOrderService returnOrderService = sellPlatformContext.getPlatformService(request.getSellPlatformType(),
                SellPlatformServiceType.SELL_RETURN_ORDER_SERVICE);
        if (Objects.isNull(returnOrderService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return returnOrderService.updateAccountAddress(request);
    }

    @Override
    public BaseResponse<String> addReturnOrder(@Valid SellPlatformAddReturnOrderRequest request) {
        SellPlatformReturnOrderService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_RETURN_ORDER_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.addReturnOrder(request);
    }

    @Override
    public BaseResponse cancelReturnOrder(@Valid SellPlatformReturnOrderRequest request) {
        SellPlatformReturnOrderService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_RETURN_ORDER_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.cancelReturnOrder(request);
    }

    @Override
    public BaseResponse acceptReturnOrder(@Valid SellPlatformAcceptReturnOrderRequest request) {
        SellPlatformReturnOrderService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_RETURN_ORDER_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.acceptReturnOrder(request);
    }

    @Override
    public BaseResponse upReturnInfo(@Valid SellPlatformUpReturnOrderRequest request) {
        SellPlatformReturnOrderService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_RETURN_ORDER_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.upReturnInfo(request);
    }

    @Override
    public BaseResponse acceptRefund(@Valid SellPlatformReturnOrderRequest request) {
        SellPlatformReturnOrderService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_RETURN_ORDER_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.acceptRefund(request);
    }

    @Override
    public BaseResponse rejectReturn(@Valid SellPlatformReturnOrderRequest request) {
        SellPlatformReturnOrderService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_RETURN_ORDER_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.rejectReturn(request);
    }

    @Override
    public BaseResponse updateReturn(@Valid SellPlatformAddReturnOrderRequest request) {
        SellPlatformReturnOrderService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_RETURN_ORDER_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.updateReturn(request);
    }
}