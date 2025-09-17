package com.wanmi.sbc.ledgeraccount;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountSaveRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.ReceiverSaveRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountSaveResponse;
import com.wanmi.sbc.customer.bean.enums.LedgerAccountType;
import com.wanmi.sbc.customer.bean.enums.LedgerReceiverType;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@Tag(name =  "清分账户管理API", description =  "LedgerAccountController")
@RestController
@Validated
@RequestMapping(value = "/ledgeraccount")
public class ProviderLedgerAccountController {

    @Autowired
    private LedgerAccountProvider ledgerAccountProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private LedgerAccountBaseService ledgerAccountBaseService;

    @Operation(summary = "保存清分账户")
    @PostMapping("/save")
    public BaseResponse<LedgerAccountSaveResponse> saveAccount(@RequestBody @Valid ReceiverSaveRequest addReq) {
        ledgerAccountBaseService.checkGatewayOpen();
        LedgerAccountSaveRequest request = KsBeanUtil.convert(addReq, LedgerAccountSaveRequest.class);
        request.setAccountType(LedgerAccountType.RECEIVER.toValue());
        request.setReceiverType(LedgerReceiverType.PROVIDER.toValue());
        request.setBusinessId(commonUtil.getCompanyInfoId().toString());
        return ledgerAccountProvider.saveAccount(request);
    }


}
