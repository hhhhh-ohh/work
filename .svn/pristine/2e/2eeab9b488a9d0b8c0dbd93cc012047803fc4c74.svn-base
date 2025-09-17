package com.wanmi.sbc.account.provider.impl.ledgerfunds;

import com.wanmi.sbc.account.api.provider.ledgerfunds.LedgerFundsProvider;
import com.wanmi.sbc.account.api.request.ledgerfunds.LedgerFundsAmountRequest;
import com.wanmi.sbc.account.ledgerfunds.service.LedgerFundsService;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>会员分账资金保存服务接口实现</p>
 * @author xuyunpeng
 * @date 2022-07-25 16:54:38
 */
@RestController
@Validated
public class LedgerFundsController implements LedgerFundsProvider {
	@Autowired
	private LedgerFundsService ledgerFundsService;

	@Override
	public BaseResponse grantAmount(@RequestBody @Valid LedgerFundsAmountRequest request) {
		ledgerFundsService.grantAmount(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateWithdrawnAmount(@RequestBody @Valid LedgerFundsAmountRequest request) {
		ledgerFundsService.updateWithdrawnAmount(request);
		return BaseResponse.SUCCESSFUL();
	}
}

