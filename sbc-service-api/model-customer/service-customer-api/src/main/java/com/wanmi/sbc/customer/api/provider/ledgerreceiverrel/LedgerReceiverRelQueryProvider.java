package com.wanmi.sbc.customer.api.provider.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountCheckRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerReceiverContractApplyRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverEcApplyRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.*;
import com.wanmi.sbc.customer.api.response.ledgerreceiverrel.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>分账绑定关系查询服务Provider</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
@FeignClient(value = "${application.customer.name}", contextId = "LedgerReceiverRelQueryProvider")
public interface LedgerReceiverRelQueryProvider {

	/**
	 * 分页查询分账绑定关系API
	 *
	 * @author 许云鹏
	 * @param ledgerReceiverRelPageReq 分页请求参数和筛选对象 {@link LedgerReceiverRelPageRequest}
	 * @return 分账绑定关系分页列表信息 {@link LedgerReceiverRelPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/page")
	BaseResponse<LedgerReceiverRelPageResponse> page(@RequestBody @Valid LedgerReceiverRelPageRequest ledgerReceiverRelPageReq);

	/**
	 * 列表查询分账绑定关系API
	 *
	 * @author 许云鹏
	 * @param ledgerReceiverRelListReq 列表请求参数和筛选对象 {@link LedgerReceiverRelListRequest}
	 * @return 分账绑定关系的列表信息 {@link LedgerReceiverRelListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/list")
	BaseResponse<LedgerReceiverRelListResponse> list(@RequestBody @Valid LedgerReceiverRelListRequest ledgerReceiverRelListReq);

	/**
	 * 单个查询分账绑定关系API
	 *
	 * @author 许云鹏
	 * @param ledgerReceiverRelByIdRequest 单个查询分账绑定关系请求参数 {@link LedgerReceiverRelByIdRequest}
	 * @return 分账绑定关系详情 {@link LedgerReceiverRelByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/get-by-id")
	BaseResponse<LedgerReceiverRelByIdResponse> getById(@RequestBody @Valid LedgerReceiverRelByIdRequest ledgerReceiverRelByIdRequest);


	/**
	 * 单个查询分账绑定关系API
	 *
	 * @author 许云鹏
	 * @param ledgerReceiverRelListReq 单个查询分账绑定关系请求参数 {@link }
	 * @return 分账绑定关系详情 {@link LedgerReceiverRelByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/find-by-apply-id")
	BaseResponse<LedgerReceiverRelByIdResponse> findByApplyId(@RequestBody @Valid  LedgerReceiverRelListRequest ledgerReceiverRelListReq);

	/**
	 * 查询绑定状态API
	 *
	 * @author 许云鹏
	 * @param request 请求参数 {@link LedgerReceiverRelBindStateRequest}
	 * @return 分账绑定关系详情 {@link LedgerReceiverRelBindStateResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/find-bind-state-by-receiver-ids")
	BaseResponse<LedgerReceiverRelBindStateResponse> findUnBindStores(@RequestBody @Valid LedgerReceiverRelBindStateRequest request);

	/**
	 * 查询分销员绑定状态API
	 *
	 * @author 许云鹏
	 * @param request 请求参数 {@link DistributionBindStateRequest}
	 * @return 分账绑定关系详情 {@link LedgerReceiverRelBindStateResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/find-bind-state-by-distribution")
	BaseResponse<LedgerReceiverRelBindStateResponse> findUnBindStoresByDistribution(@RequestBody @Valid DistributionBindStateRequest request);


	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link LedgerReceiverRelExportRequest}
	 * @return 分账绑定关系数量 {@link Long}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/export/count")
	BaseResponse<Long> countForExport(@RequestBody @Valid LedgerReceiverRelExportRequest request);

	/**
	 * 查询分销员绑定状态API
	 *
	 * @author 许云鹏
	 * @param request 请求参数 {@link DistributionLedgerRelRequest}
	 * @return 分账绑定关系详情 {@link LedgerRelSupplierIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/find-supplier-id-by-distribution")
	BaseResponse<LedgerRelSupplierIdResponse> findSupplierIdByDistribution(@RequestBody @Valid DistributionLedgerRelRequest request);

	/**
	 * 申请结算授权协议API
	 *
	 * @author 许云鹏
	 * @param request 请求参数 {@link LedgerReceiverContractApplyRequest}
	 * @return 分账绑定关系详情 {@link String}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/receiver-contract/apply")
	BaseResponse<String> applyReceiverContract(@RequestBody @Valid LedgerReceiverContractApplyRequest request);

	/**
	 * 查询结算授权协议API
	 *
	 * @author 许云鹏
	 * @param request 请求参数 {@link LedgerReceiverContractApplyRequest}
	 * @return 分账绑定关系详情 {@link String}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/receiver-contract/find")
	BaseResponse<String> findReceiverContract(@RequestBody @Valid LedgerReceiverContractApplyRequest request);

	/**
	 * 检查商户与平台绑定状态API
	 *
	 * @author 许云鹏
	 * @param request 请求参数 {@link LedgerAccountCheckRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/ledger-account/check")
	BaseResponse checkLedgerAccount(@RequestBody @Valid LedgerAccountCheckRequest request);

	/**
	 * @description 电子合同ec003申请ID查询
	 * @author  edz
	 * @date: 2022/9/13 15:38
	 * @param ledgerReceiverEcApplyRequest
	 * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.customer.api.response.ledgerreceiverrel.LedgerReceiverRelByIdResponse>
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/find-ecapplyid")
	BaseResponse<LedgerReceiverRelByIdResponse> findByEcApplyId(@RequestBody LedgerReceiverEcApplyRequest ledgerReceiverEcApplyRequest);

	/**
	 * @description 商家与供应商绑定关系查询
	 * @author  edz
	 * @date: 2022/9/15 16:48
	 * @param request
	 * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.customer.api.response.ledgerreceiverrel.BindStateQueryResponse>
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/query-provider-bind")
	BaseResponse<BindStateQueryResponse> queryProviderBindStatePage(@RequestBody @Valid ProviderBindStateQueryRequest request);


	/**
	 * @description 商家与分销员绑定关系
	 * @author  edz
	 * @date: 2022/9/15 16:48
	 * @param request
	 * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.customer.api.response.ledgerreceiverrel.BindStateQueryResponse>
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/query-distribution-bind")
	BaseResponse<BindStateQueryResponse> queryDistributionBindStatePage(@RequestBody @Valid DistributionBindStateQueryRequest request);



}

