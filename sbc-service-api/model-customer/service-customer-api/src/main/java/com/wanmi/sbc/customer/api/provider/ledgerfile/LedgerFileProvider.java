package com.wanmi.sbc.customer.api.provider.ledgerfile;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileAddRequest;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileDelByIdListRequest;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileDelByIdRequest;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileModifyRequest;
import com.wanmi.sbc.customer.api.response.ledgerfile.LedgerFileAddResponse;
import com.wanmi.sbc.customer.api.response.ledgerfile.LedgerFileModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>分账文件保存服务Provider</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
@FeignClient(value = "${application.customer.name}", contextId = "LedgerFileProvider")
public interface LedgerFileProvider {

	/**
	 * 新增分账文件API
	 *
	 * @author 许云鹏
	 * @param ledgerFileAddRequest 分账文件新增参数结构 {@link LedgerFileAddRequest}
	 * @return 新增的分账文件信息 {@link LedgerFileAddResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerfile/add")
	BaseResponse<LedgerFileAddResponse> add(@RequestBody @Valid LedgerFileAddRequest ledgerFileAddRequest);

	/**
	 * 单个删除分账文件API
	 *
	 * @author 许云鹏
	 * @param ledgerFileDelByIdRequest 单个删除参数结构 {@link LedgerFileDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerfile/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid LedgerFileDelByIdRequest ledgerFileDelByIdRequest);

	/**
	 * 批量删除分账文件API
	 *
	 * @author 许云鹏
	 * @param ledgerFileDelByIdListRequest 批量删除参数结构 {@link LedgerFileDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerfile/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid LedgerFileDelByIdListRequest ledgerFileDelByIdListRequest);


	/**
	 * 编辑分账文件API
	 *
	 * @author 许云鹏
	 * @param ledgerFileModifyRequest 分账文件新增参数结构 {@link LedgerFileModifyRequest}
	 * @return 编辑的分账文件信息 {@link LedgerFileModifyRequest}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerfile/modify")
	BaseResponse<LedgerFileModifyResponse> modify(@RequestBody @Valid LedgerFileModifyRequest ledgerFileModifyRequest);

}

