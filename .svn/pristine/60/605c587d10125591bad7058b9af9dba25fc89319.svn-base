package com.wanmi.sbc.customer.provider.impl.ledger;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledger.LakalaProvider;
import com.wanmi.sbc.customer.api.request.ledger.LakalaAccountFunctionRequest;
import com.wanmi.sbc.customer.api.request.ledger.LakalaJobRequest;
import com.wanmi.sbc.customer.api.request.ledger.LedgerRequest;
import com.wanmi.sbc.customer.ledger.LedgerFunction;
import com.wanmi.sbc.customer.ledger.LedgerFunctionFactory;
import com.wanmi.sbc.customer.ledger.LedgerJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>清分账户保存服务接口实现</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@RestController
@Validated
public class LakalaController implements LakalaProvider {

	@Autowired
	private LedgerJobService lakalaJobService;

	@Autowired
	private LedgerFunctionFactory ledgerFunctionFactory;

	@Override
	public BaseResponse excuteFunction(@RequestBody @Valid LakalaAccountFunctionRequest request) {
		LedgerRequest ledgerRequest = KsBeanUtil.convert(request, LedgerRequest.class);
		LedgerFunction ledgerFunction = ledgerFunctionFactory.create(request.getType());
		ledgerFunction.excute(ledgerRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse ledgerJob(@RequestBody @Valid LakalaJobRequest request) {
		lakalaJobService.ledgerJob(request.getParam());
		return BaseResponse.SUCCESSFUL();
	}
}

