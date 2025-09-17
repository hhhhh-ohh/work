package com.wanmi.sbc.customer.provider.impl.ledgerfile;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.ledgerfile.LedgerFileQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileByIdRequest;
import com.wanmi.sbc.customer.api.response.ledgerfile.LedgerFileByIdResponse;
import com.wanmi.sbc.customer.ledgerfile.model.root.LedgerFile;
import com.wanmi.sbc.customer.ledgerfile.service.LedgerFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>分账文件查询服务接口实现</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
@RestController
@Validated
public class LedgerFileQueryController implements LedgerFileQueryProvider {
	@Autowired
	private LedgerFileService ledgerFileService;

	@Override
	public BaseResponse<LedgerFileByIdResponse> getById(@RequestBody @Valid LedgerFileByIdRequest ledgerFileByIdRequest) {
		LedgerFile ledgerFile =
		ledgerFileService.getOne(ledgerFileByIdRequest.getId());
		return BaseResponse.success(new LedgerFileByIdResponse(ledgerFileService.wrapperVo(ledgerFile)));
	}
}

