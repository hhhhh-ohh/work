package com.wanmi.sbc.order.api.provider.thirdplatformreturn;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.linkedmall.ThirdPlatformReturnOrderReasonRequest;
import com.wanmi.sbc.order.api.response.linkedmall.ThirdPlatformReturnReasonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>linkedMall退单服务查询接口</p>
 * @Author: daiyitian
 * @Description: 退单服务查询接口
 * @Date: 2018-12-03 15:40
 */
@FeignClient(value = "${application.order.name}", contextId = "ThirdPlatformReturnOrderQueryProvider")
public interface ThirdPlatformReturnOrderQueryProvider {

    /**
     * 查询所有退货原因
     *
     * @param request  退货原因请求参数 {@link ThirdPlatformReturnOrderReasonRequest}
     * @return 退货原因列表 {@link ThirdPlatformReturnReasonResponse}
     */
    @PostMapping("/order/${application.order.version}/third-platform-return/list-return-reason")
    BaseResponse<ThirdPlatformReturnReasonResponse> listReturnReason(@RequestBody @Valid ThirdPlatformReturnOrderReasonRequest request);
}
