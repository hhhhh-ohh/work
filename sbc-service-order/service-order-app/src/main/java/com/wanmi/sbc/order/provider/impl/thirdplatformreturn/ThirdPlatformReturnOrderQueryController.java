package com.wanmi.sbc.order.provider.impl.thirdplatformreturn;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.provider.thirdplatformreturn.ThirdPlatformReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.request.linkedmall.ThirdPlatformReturnOrderReasonRequest;
import com.wanmi.sbc.order.api.response.linkedmall.ThirdPlatformReturnReasonResponse;
import com.wanmi.sbc.order.returnorder.service.ThirdPlatformReturnOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>linkedMall退单服务查询接口</p>
 */
@RestController
public class ThirdPlatformReturnOrderQueryController implements ThirdPlatformReturnOrderQueryProvider {

    @Autowired
    private ThirdPlatformReturnOrderService thirdPlatformReturnOrderService;

    @Override
    public BaseResponse<ThirdPlatformReturnReasonResponse> listReturnReason(@RequestBody @Valid ThirdPlatformReturnOrderReasonRequest request){
        return BaseResponse.success(thirdPlatformReturnOrderService.reasons(request.getRid()));
    }
}
