package com.wanmi.sbc.customer.provider.impl.ledgerfile;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgerfile.LedgerFileProvider;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileAddRequest;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileDelByIdListRequest;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileDelByIdRequest;
import com.wanmi.sbc.customer.api.request.ledgerfile.LedgerFileModifyRequest;
import com.wanmi.sbc.customer.api.response.ledgerfile.LedgerFileAddResponse;
import com.wanmi.sbc.customer.api.response.ledgerfile.LedgerFileModifyResponse;
import com.wanmi.sbc.customer.ledgerfile.model.root.LedgerFile;
import com.wanmi.sbc.customer.ledgerfile.service.LedgerFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>分账文件保存服务接口实现</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
@RestController
@Validated
public class LedgerFileController implements LedgerFileProvider {
	@Autowired
	private LedgerFileService ledgerFileService;

	@Override
	public BaseResponse<LedgerFileAddResponse> add(@RequestBody @Valid LedgerFileAddRequest ledgerFileAddRequest) {
		LedgerFile ledgerFile = KsBeanUtil.convert(ledgerFileAddRequest, LedgerFile.class);
		return BaseResponse.success(new LedgerFileAddResponse(ledgerFileService.add(ledgerFile)));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid LedgerFileDelByIdRequest ledgerFileDelByIdRequest) {
		ledgerFileService.deleteById(ledgerFileDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid LedgerFileDelByIdListRequest ledgerFileDelByIdListRequest) {
		ledgerFileService.deleteByIdList(ledgerFileDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * 编辑分账文件API
	 *
	 * @param ledgerFileModifyRequest 分账文件新增参数结构 {@link LedgerFileModifyRequest}
	 * @return 编辑的分账文件信息 {@link LedgerFileModifyRequest}
	 * @author 许云鹏
	 */
	@Override
	public BaseResponse<LedgerFileModifyResponse> modify(@RequestBody @Valid LedgerFileModifyRequest ledgerFileModifyRequest) {
		LedgerFile ledgerFile = KsBeanUtil.convert(ledgerFileModifyRequest, LedgerFile.class);
		ledgerFileService.modify(ledgerFile);
		return BaseResponse.SUCCESSFUL();
	}
}

