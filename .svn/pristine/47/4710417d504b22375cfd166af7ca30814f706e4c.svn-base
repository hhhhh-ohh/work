package com.wanmi.sbc.ledgeraccount;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.ledgercontract.LedgerContractQueryProvider;
import com.wanmi.sbc.customer.api.response.ledgercontract.LedgerContractByIdResponse;
import com.wanmi.sbc.customer.bean.vo.LedgerContractVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name =  "分账合同协议配置管理API", description =  "LedgerContractController")
@RestController
@Validated
@RequestMapping(value = "/ledgercontract")
public class LedgerContractController {

    @Autowired
    private LedgerContractQueryProvider ledgerContractQueryProvider;

    @Autowired
    private LedgerAccountBaseService ledgerAccountBaseService;

    @Operation(summary = "查询分账合同协议配置")
    @GetMapping
    public BaseResponse<LedgerContractByIdResponse> getContract() {
        ledgerAccountBaseService.checkGatewayOpen();
        LedgerContractVO ledgerContractVO = ledgerContractQueryProvider.getContract().getContext().getLedgerContractVO();
        ledgerContractVO.setContent(ledgerContractVO.getContent()
                .replace(Constants.PART_A, "")
                .replace(Constants.PART_B, ""));
        return BaseResponse.success(new LedgerContractByIdResponse(ledgerContractVO));
    }
}
