package com.wanmi.sbc.order.api.provider.trade;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.trade.GrouponInstanceByGrouponNoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Description:
 * @Date: 2022-8-23 17:24
 */
@FeignClient(value = "${application.order.name}", contextId = "GrouponInstanceProvider")
public interface GrouponInstanceProvider {


    /**
     * 条件
     *
     * @param request 带参参数 {@link GrouponInstanceByGrouponNoRequest}
     * @return
     */
    @PostMapping("/order/${application.order.version}/groupon/instance/modify-groupon-expired-sendflag-by-grouponno")
    BaseResponse modifyGrouponExpiredSendFlagByGrouponNo(@RequestBody @Valid GrouponInstanceByGrouponNoRequest request);

}

