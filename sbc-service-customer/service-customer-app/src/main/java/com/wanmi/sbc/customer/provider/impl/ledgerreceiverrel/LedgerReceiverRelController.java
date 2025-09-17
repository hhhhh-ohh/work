package com.wanmi.sbc.customer.provider.impl.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.*;
import com.wanmi.sbc.customer.api.response.ledgerreceiverrel.LedgerReceiverRelAddResponse;
import com.wanmi.sbc.customer.ledgerreceiverrel.model.root.LedgerReceiverRel;
import com.wanmi.sbc.customer.ledgerreceiverrel.service.LedgerReceiverRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>分账绑定关系保存服务接口实现</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
@RestController
@Validated
public class LedgerReceiverRelController implements LedgerReceiverRelProvider {
	@Autowired
	private LedgerReceiverRelService ledgerReceiverRelService;

	@Override
	public BaseResponse<LedgerReceiverRelAddResponse> add(@RequestBody @Valid LedgerReceiverRelAddRequest ledgerReceiverRelAddRequest) {
		LedgerReceiverRel ledgerReceiverRel = KsBeanUtil.convert(ledgerReceiverRelAddRequest, LedgerReceiverRel.class);
		return BaseResponse.success(new LedgerReceiverRelAddResponse(
				ledgerReceiverRelService.wrapperVo(ledgerReceiverRelService.add(ledgerReceiverRel))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid LedgerReceiverRelDelByIdRequest ledgerReceiverRelDelByIdRequest) {
		ledgerReceiverRelService.deleteById(ledgerReceiverRelDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid LedgerReceiverRelDelByIdListRequest ledgerReceiverRelDelByIdListRequest) {
		ledgerReceiverRelService.deleteByIdList(ledgerReceiverRelDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateBindState(@RequestBody @Valid LedgerReceiverRelUpdateBindStateRequest ledgerReceiverRelUpdateBindStateRequest) {
		ledgerReceiverRelService.updateBindState(ledgerReceiverRelUpdateBindStateRequest.getBindState(),
				ledgerReceiverRelUpdateBindStateRequest.getRejectReason(),
				ledgerReceiverRelUpdateBindStateRequest.getBindTime(),
				ledgerReceiverRelUpdateBindStateRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse batchAddByAccountId(@RequestBody @Valid LedgerReceiverBatchRequest request) {
		ledgerReceiverRelService.batchAddByAccountId(request.getAccountId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateNameAndCode(@RequestBody @Valid LedgerInfoUpdateRequest request) {
		ledgerReceiverRelService.updateCompanyInfo(request.getCompanyInfoId(), request.getName());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse checkBindInfo(@RequestBody @Valid LedgerReceiverRelCheckRequest request) {
		ledgerReceiverRelService.checkBIndInfo(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateEC003Info(@RequestBody UpdateEC003InfoRequest updateEC003InfoRequest) {
		ledgerReceiverRelService.updateEC003Info(updateEC003InfoRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse applyBind(@RequestBody @Valid LedgerReceiverRelApplyRequest request) {
		ledgerReceiverRelService.distributionApply(request.getSupplierId(), request.getCustomerId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse saveBindContractFile(@RequestBody @Valid LedgerBindContractFileSaveRequest request) {
		ledgerReceiverRelService.saveBindContractFile(request);
		return BaseResponse.SUCCESSFUL();
	}


}

