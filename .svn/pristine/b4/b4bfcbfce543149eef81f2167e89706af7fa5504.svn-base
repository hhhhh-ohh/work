package com.wanmi.sbc.account.provider.impl.credit;

import com.wanmi.sbc.account.api.provider.credit.CreditOverviewProvider;
import com.wanmi.sbc.account.api.response.credit.CreditOverviewResponse;
import com.wanmi.sbc.account.credit.service.CreditOverviewService;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 授信总览服务
 * @author zhengyang
 * @date 2021-03-12 16:21:28
 */
@RestController
public class CreditOverviewController implements CreditOverviewProvider {

    @Resource
    private CreditOverviewService creditOverviewService;

    @Override
    public BaseResponse<CreditOverviewResponse> findCreditOverview() {
        return BaseResponse.success(creditOverviewService.findCreditOverview());
    }
}

