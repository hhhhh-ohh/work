package com.wanmi.sbc.customer.api.provider.ledgercontract;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.ledgercontract.LedgerContractSaveRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>分账合同协议配置服务Provider</p>
 * @author 许云鹏
 * @date 2022-07-07 17:54:08
 */
@FeignClient(value = "${application.customer.name}", contextId = "LedgerContractProvider")
public interface LedgerContractProvider {

    /**
     * 编辑分账合同协议配置API
     *
     * @author 许云鹏
     * @param
     * @return
     */
    @PostMapping("/customer/${application.customer.version}/ledgercontract/save")
    BaseResponse save(@RequestBody @Valid LedgerContractSaveRequest request);
}
