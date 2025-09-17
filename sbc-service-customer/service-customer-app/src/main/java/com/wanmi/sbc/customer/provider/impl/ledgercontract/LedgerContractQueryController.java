package com.wanmi.sbc.customer.provider.impl.ledgercontract;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.ledgercontract.LedgerContractQueryProvider;
import com.wanmi.sbc.customer.api.response.ledgercontract.LedgerContractByIdResponse;
import com.wanmi.sbc.customer.ledgercontract.model.root.LedgerContract;
import com.wanmi.sbc.customer.ledgercontract.service.LedgerContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>分账合同协议配置查询服务接口实现</p>
 * @author 许云鹏
 * @date 2022-07-07 17:54:08
 */
@RestController
@Validated
public class LedgerContractQueryController implements LedgerContractQueryProvider {
	@Autowired
	private LedgerContractService ledgerContractService;

	@Override
	public BaseResponse<LedgerContractByIdResponse> getContract() {
		LedgerContract ledgerContract = ledgerContractService.getOne();
		return BaseResponse.success(new LedgerContractByIdResponse(ledgerContractService.wrapperVo(ledgerContract)));
	}
}

