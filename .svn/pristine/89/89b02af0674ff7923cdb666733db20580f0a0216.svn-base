package com.wanmi.sbc.customer.provider.impl.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountCheckRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerReceiverContractApplyRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverEcApplyRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.*;
import com.wanmi.sbc.customer.api.response.ledgerreceiverrel.*;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;
import com.wanmi.sbc.customer.ledgerreceiverrel.model.root.LedgerReceiverRel;
import com.wanmi.sbc.customer.ledgerreceiverrel.service.LedgerReceiverRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>分账绑定关系查询服务接口实现</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
@RestController
@Validated
public class LedgerReceiverRelQueryController implements LedgerReceiverRelQueryProvider {
	@Autowired
	private LedgerReceiverRelService ledgerReceiverRelService;

	@Override
	public BaseResponse<LedgerReceiverRelPageResponse> page(@RequestBody @Valid LedgerReceiverRelPageRequest ledgerReceiverRelPageReq) {
		LedgerReceiverRelQueryRequest queryReq = KsBeanUtil.convert(ledgerReceiverRelPageReq, LedgerReceiverRelQueryRequest.class);
		Page<LedgerReceiverRel> ledgerReceiverRelPage = ledgerReceiverRelService.page(queryReq);
		Page<LedgerReceiverRelVO> newPage = ledgerReceiverRelPage.map(entity -> ledgerReceiverRelService.wrapperVo(entity));
		MicroServicePage<LedgerReceiverRelVO> microPage = new MicroServicePage<>(newPage, ledgerReceiverRelPageReq.getPageable());
		LedgerReceiverRelPageResponse finalRes = new LedgerReceiverRelPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<LedgerReceiverRelListResponse> list(@RequestBody @Valid LedgerReceiverRelListRequest ledgerReceiverRelListReq) {
		LedgerReceiverRelQueryRequest queryReq = KsBeanUtil.convert(ledgerReceiverRelListReq, LedgerReceiverRelQueryRequest.class);
		List<LedgerReceiverRel> ledgerReceiverRelList = ledgerReceiverRelService.list(queryReq);
		List<LedgerReceiverRelVO> newList = ledgerReceiverRelList.stream().map(entity -> ledgerReceiverRelService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new LedgerReceiverRelListResponse(newList));
	}

	@Override
	public BaseResponse<LedgerReceiverRelByIdResponse> getById(@RequestBody @Valid LedgerReceiverRelByIdRequest ledgerReceiverRelByIdRequest) {
		LedgerReceiverRel ledgerReceiverRel =
		ledgerReceiverRelService.getOne(ledgerReceiverRelByIdRequest.getId());
		return BaseResponse.success(new LedgerReceiverRelByIdResponse(ledgerReceiverRelService.wrapperVo(ledgerReceiverRel)));
	}

	@Override
	public BaseResponse<LedgerReceiverRelByIdResponse> findByApplyId(@RequestBody @Valid LedgerReceiverRelListRequest ledgerReceiverRelListReq) {
		LedgerReceiverRelVO ledgerReceiverRelVO =
				ledgerReceiverRelService.findByApplyId(ledgerReceiverRelListReq.getApplyId());
		return BaseResponse.success(new LedgerReceiverRelByIdResponse(ledgerReceiverRelVO));
	}

	@Override
	public BaseResponse<LedgerReceiverRelBindStateResponse> findUnBindStores(@RequestBody @Valid LedgerReceiverRelBindStateRequest request) {
		List<Long> unBindList = ledgerReceiverRelService.findUnBindList(request.getSupplierId(), request.getReceiverStoreIds());
		return BaseResponse.success(new LedgerReceiverRelBindStateResponse(unBindList));
	}

	@Override
	public BaseResponse<LedgerReceiverRelBindStateResponse> findUnBindStoresByDistribution(@RequestBody @Valid DistributionBindStateRequest request) {
		List<Long> unBindList = ledgerReceiverRelService.findUnBindList(request.getCustomerId(), request.getStoreIds());
		return BaseResponse.success(new LedgerReceiverRelBindStateResponse(unBindList));
	}


	@Override
	public BaseResponse<Long> countForExport(@Valid LedgerReceiverRelExportRequest request) {
		LedgerReceiverRelQueryRequest queryReq = KsBeanUtil.convert(request, LedgerReceiverRelQueryRequest.class);
		Long total = ledgerReceiverRelService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<LedgerRelSupplierIdResponse> findSupplierIdByDistribution(@RequestBody @Valid DistributionLedgerRelRequest request) {
		List<Long> storeIds = ledgerReceiverRelService.findSupplierIdByReceiverId(request.getCustomerId());
		return BaseResponse.success(new LedgerRelSupplierIdResponse(storeIds));
	}

	@Override
	public BaseResponse<String> applyReceiverContract(@RequestBody @Valid LedgerReceiverContractApplyRequest request) {
		String url = ledgerReceiverRelService.applyReceiverContract(request.getRelId(), request.getSupplierId());
		return BaseResponse.success(url);
	}

	@Override
	public BaseResponse<String> findReceiverContract(@RequestBody @Valid LedgerReceiverContractApplyRequest request) {
		String receiverContract = ledgerReceiverRelService.findReceiverContract(request.getRelId(), request.getSupplierId());
		return BaseResponse.success(receiverContract);
	}

	@Override
	public BaseResponse checkLedgerAccount(@RequestBody @Valid LedgerAccountCheckRequest request) {
		ledgerReceiverRelService.checkBindState(request.getSupplierId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<LedgerReceiverRelByIdResponse> findByEcApplyId(@RequestBody LedgerReceiverEcApplyRequest ledgerReceiverEcApplyRequest){
		LedgerReceiverRelVO ledgerReceiverRelVO =
				ledgerReceiverRelService.findByEcApplyId(ledgerReceiverEcApplyRequest.getEcApplyId());
		return BaseResponse.success(LedgerReceiverRelByIdResponse.builder().ledgerReceiverRelVO(ledgerReceiverRelVO).build());
	}

	@Override
	public BaseResponse<BindStateQueryResponse> queryProviderBindStatePage(@RequestBody @Valid ProviderBindStateQueryRequest request){
		BindStateQueryResponse response = BindStateQueryResponse.builder()
				.lakalaProviderBindVOPage(ledgerReceiverRelService.queryProviderBindStatePage(request))
				.build();
		return BaseResponse.success(response);
	}

	@Override
	public BaseResponse<BindStateQueryResponse> queryDistributionBindStatePage(@RequestBody @Valid DistributionBindStateQueryRequest request){
		BindStateQueryResponse response = BindStateQueryResponse.builder()
				.lakalaDistributionBindVOPage(ledgerReceiverRelService.queryDistributionBindStatePage(request))
				.build();
		return BaseResponse.success(response);
	}
}

