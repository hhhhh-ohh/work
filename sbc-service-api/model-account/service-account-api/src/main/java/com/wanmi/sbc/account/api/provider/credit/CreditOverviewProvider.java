package com.wanmi.sbc.account.api.provider.credit;

import com.wanmi.sbc.account.api.response.credit.CreditOverviewResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 授信总览服务
 *
 * @author zhengyuang
 * @date 2021-03-12 16:21:28
 */
@FeignClient(value = "${application.account.name}", contextId = "CreditOverviewProvider")
public interface CreditOverviewProvider {

    /**
     * 查询授信总览
     * @return {@link CreditOverviewResponse}
     */
    @PostMapping("/account/${application.account.version}/creditoverview/find-credit-overview")
    BaseResponse<CreditOverviewResponse> findCreditOverview();
}

