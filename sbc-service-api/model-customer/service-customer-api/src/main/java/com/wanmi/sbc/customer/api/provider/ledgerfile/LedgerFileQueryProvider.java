package com.wanmi.sbc.customer.api.provider.ledgerfile;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileByIdRequest;
import com.wanmi.sbc.customer.api.response.ledgerfile.LedgerFileByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>分账文件查询服务Provider</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
@FeignClient(value = "${application.customer.name}", contextId = "LedgerFileQueryProvider")
public interface LedgerFileQueryProvider {

	/**
	 * 单个查询分账文件API
	 *
	 * @author 许云鹏
	 * @param ledgerFileByIdRequest 单个查询分账文件请求参数 {@link LedgerFileByIdRequest}
	 * @return 分账文件详情 {@link LedgerFileByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerfile/get-by-id")
	BaseResponse<LedgerFileByIdResponse> getById(@RequestBody @Valid LedgerFileByIdRequest ledgerFileByIdRequest);

}

