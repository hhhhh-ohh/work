package com.wanmi.sbc.order.api.provider.optimization;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.api.response.trade.TradeCommitResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author zhanggaolei
 */
@FeignClient(value = "${application.order.name}", contextId = "Trade1Provider")
public interface Trade1Provider {

    @PostMapping("/order/${application.order.version}/trade1/commit")
    BaseResponse<TradeCommitResponse> commit(@RequestBody @Valid TradeCommitRequest tradeCommitRequest);

}
