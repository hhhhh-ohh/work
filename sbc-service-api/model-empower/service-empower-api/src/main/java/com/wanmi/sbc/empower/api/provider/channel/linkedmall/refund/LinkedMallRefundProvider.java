package com.wanmi.sbc.empower.api.provider.channel.linkedmall.refund;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallInitApplyRefundRequest;
import com.wanmi.sbc.empower.api.response.channel.linkedmall.LinkedMallInitApplyRefundResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(value = "${application.empower.name}",contextId = "LinkedMallRefundProvider")
public interface LinkedMallRefundProvider {

    /**
     * 订单逆向渲染
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/linkedmall/initApplyRefund")
    BaseResponse<LinkedMallInitApplyRefundResponse> initApplyRefund(@RequestBody @Valid LinkedMallInitApplyRefundRequest request);

}
