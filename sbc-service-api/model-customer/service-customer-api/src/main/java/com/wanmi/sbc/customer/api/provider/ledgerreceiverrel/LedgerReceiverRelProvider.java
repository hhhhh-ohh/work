package com.wanmi.sbc.customer.api.provider.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.*;
import com.wanmi.sbc.customer.api.response.ledgerreceiverrel.LedgerReceiverRelAddResponse;
import com.wanmi.sbc.customer.api.response.ledgerreceiverrel.LedgerReceiverRelModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>分账绑定关系保存服务Provider</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
@FeignClient(value = "${application.customer.name}", contextId = "LedgerReceiverRelProvider")
public interface LedgerReceiverRelProvider {

	/**
	 * 新增分账绑定关系API
	 *
	 * @author 许云鹏
	 * @param ledgerReceiverRelAddRequest 分账绑定关系新增参数结构 {@link LedgerReceiverRelAddRequest}
	 * @return 新增的分账绑定关系信息 {@link LedgerReceiverRelAddResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/add")
	BaseResponse<LedgerReceiverRelAddResponse> add(@RequestBody @Valid LedgerReceiverRelAddRequest ledgerReceiverRelAddRequest);

	/**
	 * 单个删除分账绑定关系API
	 *
	 * @author 许云鹏
	 * @param ledgerReceiverRelDelByIdRequest 单个删除参数结构 {@link LedgerReceiverRelDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid LedgerReceiverRelDelByIdRequest ledgerReceiverRelDelByIdRequest);

	/**
	 * 批量删除分账绑定关系API
	 *
	 * @author 许云鹏
	 * @param ledgerReceiverRelDelByIdListRequest 批量删除参数结构 {@link LedgerReceiverRelDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid LedgerReceiverRelDelByIdListRequest ledgerReceiverRelDelByIdListRequest);


	/**
	 * 修改分账绑定关系API
	 *
	 * @author 许云鹏
	 * @param ledgerReceiverRelModifyRequest 分账绑定关系修改参数结构 {@link LedgerReceiverRelModifyRequest}
	 * @return 修改的分账绑定关系信息 {@link LedgerReceiverRelModifyResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/update-bind-state")
	BaseResponse updateBindState(@RequestBody @Valid LedgerReceiverRelUpdateBindStateRequest ledgerReceiverRelModifyRequest);


	/**
	 * 批量保存分账绑定关系API
	 *
	 * @author 许云鹏
	 * @param request 参数结构 {@link LedgerReceiverBatchRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/batch-add-by-account_id")
	BaseResponse batchAddByAccountId(@RequestBody @Valid LedgerReceiverBatchRequest request);

	/**
	 * 修改公司或分销员信息API
	 *
	 * @author 许云鹏
	 * @param request 参数结构 {@link LedgerInfoUpdateRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/udpate-name-and-code")
	BaseResponse updateNameAndCode(@RequestBody @Valid LedgerInfoUpdateRequest request);

	/**
	 * 审核分销员绑定申请API
	 *
	 * @author 许云鹏
	 * @param request 参数结构 {@link LedgerReceiverRelCheckRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/check")
	BaseResponse checkBindInfo(@RequestBody @Valid LedgerReceiverRelCheckRequest request);

	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/update-EC003")
	BaseResponse updateEC003Info(@RequestBody UpdateEC003InfoRequest updateEC003InfoRequest);

	/**
	 * 分销员绑定申请API
	 *
	 * @author 许云鹏
	 * @param request 参数结构 {@link LedgerReceiverRelApplyRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/apply")
	BaseResponse applyBind(@RequestBody @Valid LedgerReceiverRelApplyRequest request);

	/**
	 * 保存分账合作协议文件API
	 *
	 * @author 许云鹏
	 * @param request 参数结构 {@link LedgerBindContractFileSaveRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrel/save-bind-contract-file")
	BaseResponse saveBindContractFile(@RequestBody @Valid LedgerBindContractFileSaveRequest request);
}

