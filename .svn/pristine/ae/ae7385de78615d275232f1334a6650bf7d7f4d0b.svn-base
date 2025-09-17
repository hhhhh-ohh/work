package com.wanmi.sbc.customer.provider.impl.ledgercontract;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.ledgercontract.LedgerContractProvider;
import com.wanmi.sbc.customer.api.request.ledgercontract.LedgerContractSaveRequest;
import com.wanmi.sbc.customer.ledgercontract.service.LedgerContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author xuyunpeng
 * @className LedgerContractController
 * @description 分账合同协议配置服务接口实现
 * @date 2022/9/24 10:11 AM
 **/
@RestController
@Validated
public class LedgerContractController implements LedgerContractProvider {

    @Autowired
    private LedgerContractService ledgerContractService;

    @Override
    public BaseResponse save(@RequestBody @Valid LedgerContractSaveRequest request) {
        ledgerContractService.save(request.getBody(), request.getContent());
        return BaseResponse.SUCCESSFUL();
    }
}
