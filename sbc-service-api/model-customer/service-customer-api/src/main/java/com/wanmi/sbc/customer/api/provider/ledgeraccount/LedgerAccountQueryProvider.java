package com.wanmi.sbc.customer.api.provider.ledgeraccount;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.api.request.ledgeraccount.*;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelPageMobileRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.DistributionApplyRecordRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.SupplierApplyRecordRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.*;
import com.wanmi.sbc.customer.bean.vo.DistributionApplyRecordVO;
import com.wanmi.sbc.customer.bean.vo.SupplierApplyRecordVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>清分账户查询服务Provider</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@FeignClient(value = "${application.customer.name}", contextId = "LedgerAccountQueryProvider")
public interface LedgerAccountQueryProvider {

	/**
	 * 分页查询清分账户API
	 *
	 * @author 许云鹏
	 * @param ledgerAccountPageReq 分页请求参数和筛选对象 {@link LedgerAccountPageRequest}
	 * @return 清分账户分页列表信息 {@link LedgerAccountPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/page")
	BaseResponse<LedgerAccountPageResponse> page(@RequestBody @Valid LedgerAccountPageRequest ledgerAccountPageReq);

	/**
	 * 列表查询清分账户API
	 *
	 * @author 许云鹏
	 * @param ledgerAccountListReq 列表请求参数和筛选对象 {@link LedgerAccountListRequest}
	 * @return 清分账户的列表信息 {@link LedgerAccountListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/list")
	BaseResponse<LedgerAccountListResponse> list(@RequestBody @Valid LedgerAccountListRequest ledgerAccountListReq);

	/**
	 * 单个查询清分账户API
	 *
	 * @author 许云鹏
	 * @param ledgerAccountByIdRequest 单个查询清分账户请求参数 {@link LedgerAccountFindRequest}
	 * @return 清分账户详情 {@link LedgerAccountByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/get-by-id")
	BaseResponse<LedgerAccountByIdResponse> getById(@RequestBody @Valid LedgerAccountFindRequest ledgerAccountByIdRequest);



	/**
	 * 单个查询清分账户API
	 *
	 * @author 许云鹏
	 * @param ledgerAccountListRequest 单个查询清分账户请求参数 {@link LedgerAccountListRequest}
	 * @return 清分账户详情 {@link LedgerAccountByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/find-by-ledger-apply-id")
	BaseResponse<LedgerAccountByIdResponse> findByLedgerApplyId(@RequestBody @Valid LedgerAccountListRequest ledgerAccountListRequest);


	/**
	 * 单个查询清分账户API
	 *
	 * @author 许云鹏
	 * @param ledgerAccountListRequest 单个查询清分账户请求参数 {@link LedgerAccountListRequest}
	 * @return 清分账户详情 {@link LedgerAccountByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/find-by-ec-apply-id")
	BaseResponse<LedgerAccountByIdResponse> findByEcApplyId(@RequestBody @Valid LedgerAccountListRequest ledgerAccountListRequest);


	/**
	 * 校验Boss清分账户是否开通API
	 *
	 * @author 许云鹏
	 * @param
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/check-boss-account")
	BaseResponse checkBossAccount();

	/**
	 * 进件校验API
	 *
	 * @author 许云鹏
	 * @param
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/verify-contract-info")
	BaseResponse verifyContractInfo(@RequestBody @Valid LedgerVerifyContractInfoRequest request);


	/**
	 * 列表查询清分账户API
	 *
	 * @author 许云鹏
	 * @param ledgerAccountListReq 列表请求参数和筛选对象 {@link LedgerAccountListRequest}
	 * @return 清分账户的列表信息 {@link LedgerAccountListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/findByContractId")
	BaseResponse<LedgerAccountByIdResponse> findByContractId(@RequestBody @Valid LedgerAccountListRequest ledgerAccountListReq);

	/**
	 * 检查分销员账户状态API
	 *
	 * @author 许云鹏
	 * @param request 列表请求参数和筛选对象 {@link DistributionAccountCheckRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/check-distribution-state")
	BaseResponse<LedgerAccountCheckResponse> checkDistributionState(@RequestBody @Valid DistributionAccountCheckRequest request);

	/**
	 * @description 查询账户基本信息
	 * @author  edz
	 * @date: 2022/7/19 20:07
     * @param ledgerAccountByIdRequest
	 * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountByIdResponse>
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/find-business")
	BaseResponse<LedgerAccountByIdResponse> findByBusiness(@RequestBody @Valid LedgerAccountFindRequest ledgerAccountByIdRequest);

	@PostMapping("/customer/${application.customer.version}/ledgeraccount/find-BusinessIds")
	BaseResponse<QueryByBusinessIdsResponse> findByBusinessIds(@RequestBody @Valid QueryByBusinessIdsRequest request);

	/**
	 * @description 查询商户协议
	 * @author  edz
	 * @date: 2022/7/19 20:LedgerSupplierContractRequest
	 * @param request
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/supplier-contract")
	BaseResponse<LedgerContractResponse> findSupplierContract(@RequestBody @Valid LedgerSupplierContractRequest request);


	/**
	 * @description 查询回调补偿的异常数据
	 * @author  edz
	 * @date: 2022/7/19 20:LedgerSupplierContractRequest
	 * @param
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/find-for-call-back")
	BaseResponse<LedgerCallBackResponse> findForCallback();

	/**
	 * @description 商家、供应商进件记录
	 * @author  edz
	 * @date: 2022/9/8 10:11
	 * @param request
	 * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.common.base.MicroServicePage<com.wanmi.sbc.customer.bean.vo.SupplierApplyRecordVO>>
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/supplier-apply-record-page")
	BaseResponse<MicroServicePage<SupplierApplyRecordVO>> supplierApplyRecordPage(@RequestBody @Valid SupplierApplyRecordRequest request);

	/**
	 * @description 分销员进件记录
	 * @author  edz
	 * @date: 2022/9/8 10:12
	 * @param request
	 * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.common.base.MicroServicePage<com.wanmi.sbc.customer.bean.vo.DistributionApplyRecordVO>>
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/distribution-apply-record-page")
	BaseResponse<MicroServicePage<DistributionApplyRecordVO>> distributionApplyRecordPage(@RequestBody @Valid DistributionApplyRecordRequest request);

	/**
	 * 分页查询清分账户API
	 *
	 * @author 许云鹏
	 * @param request 分页请求参数和筛选对象 {@link LedgerReceiverRelPageMobileRequest}
	 * @return 清分账户分页列表信息 {@link LedgerAccountPageMobileResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/page-mobile")
	BaseResponse<LedgerAccountPageMobileResponse> pageMobile(@RequestBody @Valid LedgerReceiverRelPageMobileRequest request);

	/**
	 * 查询清分账户图片API
	 *
	 * @author 许云鹏
	 * @param request 查询清分账户图片请求参数 {@link LedgerAccountPicFindRequest}
	 * @return 清分账户详情 {@link LedgerAccountPicResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/get-pic-by-id")
	BaseResponse<LedgerAccountPicResponse> getPicById(@RequestBody @Valid LedgerAccountPicFindRequest request);

	/****
	 * 根据b2bAddApplyId查询清分账户API
	 *
	 * @author zhangwenchang
	 * @param ledgerAccountListReq 查询清分账户请求参数 {@link LedgerAccountListRequest}
	 * @return 清分账户详情 {@link LedgerAccountByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/findByB2bAddApplyId")
	BaseResponse<LedgerAccountByIdResponse> findByB2bAddApplyId(@RequestBody @Valid LedgerAccountListRequest ledgerAccountListReq);
}

