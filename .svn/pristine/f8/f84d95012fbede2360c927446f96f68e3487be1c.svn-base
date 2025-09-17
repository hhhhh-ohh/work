package com.wanmi.sbc.customer.provider.impl.ledgeraccount;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountContractRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountModifyRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountSaveRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountSaveResponse;
import com.wanmi.sbc.customer.bean.enums.LedgerAccountType;
import com.wanmi.sbc.customer.ledgeraccount.model.root.LedgerAccount;
import com.wanmi.sbc.customer.ledgeraccount.service.LedgerAccountService;
import com.wanmi.sbc.customer.mq.ProducerService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>清分账户保存服务接口实现</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@RestController
@Validated
public class LedgerAccountController implements LedgerAccountProvider {
	@Autowired
	private LedgerAccountService ledgerAccountService;

	@Autowired
	private ProducerService producerService;

	@Override
	@Transactional
	public BaseResponse<LedgerAccountSaveResponse> saveAccount(@RequestBody @Valid LedgerAccountSaveRequest ledgerAccountSaveRequest) {
		LedgerAccount ledgerAccount = KsBeanUtil.convert(ledgerAccountSaveRequest, LedgerAccount.class);
		String url = ledgerAccountService.save(ledgerAccount);
		//接收方通知调用拉卡拉接口
		if (LedgerAccountType.RECEIVER.toValue() == ledgerAccount.getAccountType()) {
			producerService.saveLedgerReceiver(ledgerAccount.getBusinessId());
		}
		return BaseResponse.success(new LedgerAccountSaveResponse(url));
	}

	@Override
	public BaseResponse modifyAccountInfo(@RequestBody @Valid LedgerAccountModifyRequest request) {
		ledgerAccountService.modifyAccountInfo(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateEcContentByBusinessId(@RequestBody @Valid LedgerAccountModifyRequest request) {
		ledgerAccountService.updateEcContentByBusinessId(request.getEcContent(),request.getEcNo(),request.getBusinessId());
		return BaseResponse.SUCCESSFUL();
	}


	@Override
	public BaseResponse updateAccountByContractId(@RequestBody @Valid LedgerAccountModifyRequest request) {
		ledgerAccountService.updateAccountByContractId(request.getAccountState(),request.getThirdMemNo(),
				request.getMerCupNo(),request.getTermNo(),request.getLedgerApplyId(),request.getContractId(),
				request.getAccountRejectReason(), request.getPassDateTime(), request.getBankTermNo(), request.getUnionTermNo(), request.getQuickTermNo());
		return BaseResponse.SUCCESSFUL();
	}


	@Override
	public BaseResponse updateAccountById(@RequestBody @Valid LedgerAccountModifyRequest request) {
		ledgerAccountService.updateAccountById(request.getLedgerState(), request.getBusinessId(), request.getLedgerRejectReason());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse acceptContract(@RequestBody @Valid LedgerAccountContractRequest request) {
		ledgerAccountService.acceptContract(request.getBusinessId());
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * 更新网银新增状态
	 *
	 * @param request
	 * @return
	 */
	@Override
	public BaseResponse updateB2bAddStateById(@RequestBody @Valid LedgerAccountModifyRequest request) {
		ledgerAccountService.updateB2bAddStateById(request.getB2bAddState(), request.getBusinessId(), request.getB2bAddApplyId());
		return BaseResponse.SUCCESSFUL();
	}
}

