package com.wanmi.sbc.customer.provider.impl.ledgeraccount;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.*;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelPageMobileRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.DistributionApplyRecordRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.SupplierApplyRecordRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.*;
import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.customer.ledgeraccount.model.root.LedgerAccount;
import com.wanmi.sbc.customer.ledgeraccount.service.LedgerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>清分账户查询服务接口实现</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@RestController
@Validated
public class LedgerAccountQueryController implements LedgerAccountQueryProvider {
	@Autowired
	private LedgerAccountService ledgerAccountService;

	@Override
	public BaseResponse<LedgerAccountPageResponse> page(@RequestBody @Valid LedgerAccountPageRequest ledgerAccountPageReq) {
		LedgerAccountQueryRequest queryReq = KsBeanUtil.convert(ledgerAccountPageReq, LedgerAccountQueryRequest.class);
		Page<LedgerAccount> ledgerAccountPage = ledgerAccountService.page(queryReq);
		Page<LedgerAccountVO> newPage = ledgerAccountPage.map(entity -> ledgerAccountService.wrapperVo(entity));
		MicroServicePage<LedgerAccountVO> microPage = new MicroServicePage<>(newPage, ledgerAccountPageReq.getPageable());
		LedgerAccountPageResponse finalRes = new LedgerAccountPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<LedgerAccountListResponse> list(@RequestBody @Valid LedgerAccountListRequest ledgerAccountListReq) {
		LedgerAccountQueryRequest queryReq = KsBeanUtil.convert(ledgerAccountListReq, LedgerAccountQueryRequest.class);
		List<LedgerAccount> ledgerAccountList = ledgerAccountService.list(queryReq);
		List<LedgerAccountVO> newList = ledgerAccountList.stream().map(entity -> ledgerAccountService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new LedgerAccountListResponse(newList));
	}

	@Override
	public BaseResponse<LedgerAccountByIdResponse> getById(@RequestBody @Valid LedgerAccountFindRequest ledgerAccountByIdRequest) {
		LedgerAccountVO ledgerAccount = ledgerAccountService
				.findByBusiness(ledgerAccountByIdRequest.getBusinessId(), ledgerAccountByIdRequest.getSetFileFlag());
		return BaseResponse.success(new LedgerAccountByIdResponse(ledgerAccount));
	}

	@Override
	public BaseResponse<LedgerAccountByIdResponse> findByLedgerApplyId(@RequestBody @Valid LedgerAccountListRequest ledgerAccountListReq) {
		LedgerAccount ledgerAccount = ledgerAccountService
				.findByLedgerApplyId(ledgerAccountListReq.getLedgerApplyId());
		return BaseResponse.success(new LedgerAccountByIdResponse(KsBeanUtil.convert(ledgerAccount,LedgerAccountVO.class)));
	}

	@Override
	public BaseResponse<LedgerAccountByIdResponse> findByEcApplyId(@RequestBody @Valid LedgerAccountListRequest ledgerAccountListReq) {
		LedgerAccount ledgerAccount = ledgerAccountService.findByEcApplyId(ledgerAccountListReq.getEcApplyId());
		return BaseResponse.success(new LedgerAccountByIdResponse(KsBeanUtil.convert(ledgerAccount,LedgerAccountVO.class)));
	}

	@Override
	public BaseResponse checkBossAccount() {
		ledgerAccountService.checkBossAccount();
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse verifyContractInfo(@RequestBody @Valid LedgerVerifyContractInfoRequest request) {
		ledgerAccountService.checkData(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<LedgerAccountByIdResponse> findByContractId(@RequestBody @Valid LedgerAccountListRequest ledgerAccountListReq) {
		LedgerAccount ledgerAccount = ledgerAccountService
				.findByContractId(ledgerAccountListReq.getContractId());
		return BaseResponse.success(new LedgerAccountByIdResponse(KsBeanUtil.convert(ledgerAccount,LedgerAccountVO.class)));
	}

	@Override
	public BaseResponse<LedgerAccountCheckResponse> checkDistributionState(@RequestBody @Valid DistributionAccountCheckRequest request) {
		Integer state = ledgerAccountService.checkDistributionAccountState(request.getBusinessId());
		return BaseResponse.success(new LedgerAccountCheckResponse(state));
	}

	@Override
	public BaseResponse<LedgerAccountByIdResponse> findByBusiness(@RequestBody @Valid LedgerAccountFindRequest ledgerAccountByIdRequest) {
		LedgerAccountVO ledgerAccount = ledgerAccountService.findByBusiness(ledgerAccountByIdRequest.getBusinessId());
		return BaseResponse.success(new LedgerAccountByIdResponse(ledgerAccount));
	}

	@Override
	public BaseResponse<QueryByBusinessIdsResponse> findByBusinessIds(@RequestBody @Valid QueryByBusinessIdsRequest request) {
		Map<String, LedgerAccountVO> businessIdToLedgerAccountVOMap =
				ledgerAccountService.findByBusiness(request.getBusinessIds());
		return BaseResponse.success(new QueryByBusinessIdsResponse(businessIdToLedgerAccountVOMap));
	}

	@Override
	public BaseResponse<LedgerContractResponse> findSupplierContract(@RequestBody @Valid LedgerSupplierContractRequest request) {
		String contract = ledgerAccountService.getSupplierContract(request.getCompanyInfoId());
		return BaseResponse.success(new LedgerContractResponse(contract));
	}

	@Override
	public BaseResponse<LedgerCallBackResponse> findForCallback() {
		LedgerCallbackVO ledgerCallbackVO = ledgerAccountService.findForCallback();
		return BaseResponse.success(LedgerCallBackResponse.builder()
				.ledgerCallbackVO(ledgerCallbackVO)
				.build());
	}

	@Override
	public BaseResponse<MicroServicePage<SupplierApplyRecordVO>> supplierApplyRecordPage(@RequestBody @Valid SupplierApplyRecordRequest request){
		MicroServicePage<SupplierApplyRecordVO> page = ledgerAccountService.supplierApplyRecordPage(request);
		return BaseResponse.success(page);
	}

	@Override
	public BaseResponse<MicroServicePage<DistributionApplyRecordVO>> distributionApplyRecordPage(@RequestBody @Valid DistributionApplyRecordRequest request){
		MicroServicePage<DistributionApplyRecordVO> page = ledgerAccountService.distributionApplyRecordPage(request);
		return BaseResponse.success(page);
	}

	@Override
	public BaseResponse<LedgerAccountPageMobileResponse> pageMobile(@RequestBody @Valid LedgerReceiverRelPageMobileRequest request) {
		MicroServicePage<LedgerRelMobileVO> page = ledgerAccountService.pageMobile(request);
		return BaseResponse.success(new LedgerAccountPageMobileResponse(page));
	}

	@Override
	public BaseResponse<LedgerAccountPicResponse> getPicById(@RequestBody @Valid LedgerAccountPicFindRequest request) {
		LedgerAccountPicResponse response = ledgerAccountService.getAccountPic(request.getBusinessId());
		return BaseResponse.success(response);
	}

	@Override
	public BaseResponse<LedgerAccountByIdResponse> findByB2bAddApplyId(@RequestBody @Valid LedgerAccountListRequest ledgerAccountListReq) {
		LedgerAccount ledgerAccount = ledgerAccountService
				.findByB2bAddApplyId(ledgerAccountListReq.getB2bAddApplyId());
		return BaseResponse.success(new LedgerAccountByIdResponse(KsBeanUtil.convert(ledgerAccount,LedgerAccountVO.class)));
	}
}

