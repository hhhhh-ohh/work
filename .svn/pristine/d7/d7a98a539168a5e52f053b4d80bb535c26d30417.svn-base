package com.wanmi.sbc.account.api.provider.ledgerfunds;

import com.wanmi.sbc.account.api.request.ledgerfunds.LedgerFundsAmountRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>会员分账资金保存服务Provider</p>
 * @author xuyunpeng
 * @date 2022-07-25 16:54:38
 */
@FeignClient(value = "${application.account.name}", contextId = "LedgerFundsProvider")
public interface LedgerFundsProvider {

	/**
	 * 分账资金提现API
	 *
	 * @author xuyunpeng
	 * @param ledgerFundsGrantAmountRequest 参数结构 {@link LedgerFundsAmountRequest}
	 * @return
	 */
	@PostMapping("/account/${application.account.version}/ledgerfunds/grant-amount")
	BaseResponse grantAmount(@RequestBody @Valid LedgerFundsAmountRequest ledgerFundsGrantAmountRequest);

	/**
	 * 修改待提现金额API
	 *
	 * @author xuyunpeng
	 * @param ledgerFundsGrantAmountRequest 参数结构 {@link LedgerFundsAmountRequest}
	 * @return
	 */
	@PostMapping("/account/${application.account.version}/ledgerfunds/update-withdrawn-amount")
	BaseResponse updateWithdrawnAmount(@RequestBody @Valid LedgerFundsAmountRequest ledgerFundsGrantAmountRequest);

}

