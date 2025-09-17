package com.wanmi.sbc.account.api.provider.ledgerfunds;

import com.wanmi.sbc.account.api.request.ledgerfunds.LedgerFundsByCustomerIdRequest;
import com.wanmi.sbc.account.api.request.ledgerfunds.LedgerFundsByIdRequest;
import com.wanmi.sbc.account.api.request.ledgerfunds.LedgerFundsListRequest;
import com.wanmi.sbc.account.api.request.ledgerfunds.LedgerFundsPageRequest;
import com.wanmi.sbc.account.api.response.ledgerfunds.LedgerFundsByIdResponse;
import com.wanmi.sbc.account.api.response.ledgerfunds.LedgerFundsListResponse;
import com.wanmi.sbc.account.api.response.ledgerfunds.LedgerFundsPageResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>会员分账资金查询服务Provider</p>
 * @author xuyunpeng
 * @date 2022-07-25 16:54:38
 */
@FeignClient(value = "${application.account.name}", contextId = "LedgerFundsQueryProvider")
public interface LedgerFundsQueryProvider {

	/**
	 * 分页查询会员分账资金API
	 *
	 * @author xuyunpeng
	 * @param ledgerFundsPageReq 分页请求参数和筛选对象 {@link LedgerFundsPageRequest}
	 * @return 会员分账资金分页列表信息 {@link LedgerFundsPageResponse}
	 */
	@PostMapping("/account/${application.account.version}/ledgerfunds/page")
	BaseResponse<LedgerFundsPageResponse> page(@RequestBody @Valid LedgerFundsPageRequest ledgerFundsPageReq);

	/**
	 * 列表查询会员分账资金API
	 *
	 * @author xuyunpeng
	 * @param ledgerFundsListReq 列表请求参数和筛选对象 {@link LedgerFundsListRequest}
	 * @return 会员分账资金的列表信息 {@link LedgerFundsListResponse}
	 */
	@PostMapping("/account/${application.account.version}/ledgerfunds/list")
	BaseResponse<LedgerFundsListResponse> list(@RequestBody @Valid LedgerFundsListRequest ledgerFundsListReq);

	/**
	 * 单个查询会员分账资金API
	 *
	 * @author xuyunpeng
	 * @param ledgerFundsByIdRequest 单个查询会员分账资金请求参数 {@link LedgerFundsByIdRequest}
	 * @return 会员分账资金详情 {@link LedgerFundsByIdResponse}
	 */
	@PostMapping("/account/${application.account.version}/ledgerfunds/get-by-id")
	BaseResponse<LedgerFundsByIdResponse> getById(@RequestBody @Valid LedgerFundsByIdRequest ledgerFundsByIdRequest);

	/**
	 * 单个查询会员分账资金API
	 *
	 * @author xuyunpeng
	 * @param request 单个查询会员分账资金请求参数 {@link LedgerFundsByCustomerIdRequest}
	 * @return 会员分账资金详情 {@link LedgerFundsByIdResponse}
	 */
	@PostMapping("/account/${application.account.version}/ledgerfunds/get-by-customer-id")
	BaseResponse<LedgerFundsByIdResponse> getByCustomerId(@RequestBody @Valid LedgerFundsByCustomerIdRequest request);

}

