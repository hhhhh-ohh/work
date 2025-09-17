package com.wanmi.sbc.customer.api.provider.ledgeraccount;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountContractRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountModifyRequest;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountSaveRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountSaveResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>清分账户保存服务Provider</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@FeignClient(value = "${application.customer.name}", contextId = "LedgerAccountProvider")
public interface LedgerAccountProvider {

	/**
	 * 新增清分账户API
	 *
	 * @author 许云鹏
	 * @param ledgerAccountSaveRequest 清分账户新增参数结构 {@link LedgerAccountSaveRequest}
	 * @return 新增的清分账户信息 {@link LedgerAccountSaveResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/save")
	BaseResponse<LedgerAccountSaveResponse> saveAccount(@RequestBody @Valid LedgerAccountSaveRequest ledgerAccountSaveRequest);

	/**
	 * 修改账户部分信息API
	 *
	 * @author 许云鹏
	 * @param request 清分账户修改参数结构 {@link LedgerAccountModifyRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/modifyAccountInfo")
	BaseResponse modifyAccountInfo(@RequestBody @Valid LedgerAccountModifyRequest request);



	/**
	 * 根据EcApplyId 更新ecContent
	 *
	 * @author 许云鹏
	 * @param request 根据EcApplyId 更新ecContent {@link LedgerAccountModifyRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/updateEcContentByEcApplyId")
	BaseResponse updateEcContentByBusinessId(@RequestBody @Valid LedgerAccountModifyRequest request);


	/**
	 * 根据contractId 更新Account
	 *
	 * @author 许云鹏
	 * @param request 根据contractId 更新Account {@link LedgerAccountModifyRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/updateAccountByContractId")
	BaseResponse updateAccountByContractId(@RequestBody @Valid LedgerAccountModifyRequest request);


	/**
	 * ledgerApplyId 更新Account
	 *
	 * @author 许云鹏
	 * @param request 根据contractId 更新Account {@link LedgerAccountModifyRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/updateAccountByLedgerApplyId")
	BaseResponse updateAccountById(@RequestBody @Valid LedgerAccountModifyRequest request);

	/**
	 * 同意协议API
	 *
	 * @author 许云鹏
	 * @param request 清分账户修改参数结构 {@link LedgerAccountContractRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/accept-contract")
	BaseResponse acceptContract(@RequestBody @Valid LedgerAccountContractRequest request);

	/**
	 * 更新网银新增状态
	 * @param request
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgeraccount/updateB2bAddStateById")
	BaseResponse updateB2bAddStateById(@RequestBody @Valid LedgerAccountModifyRequest request);
}

