package com.wanmi.sbc.account.api.provider.bank;


import com.wanmi.sbc.account.api.response.bank.BankListResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "${application.account.name}", contextId = "BankQueryProvider")
public interface BankQueryProvider {


    /**
     * 查询银行列表
     * @return {@link BankListResponse}
     */
    @PostMapping("/account/${application.account.version}/bank/list")
    BaseResponse<BankListResponse> list();
}
