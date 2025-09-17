package com.wanmi.sbc.empower.provider.impl.ledgercontent;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.empower.api.provider.ledgercontent.LedgerContentProvider;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentAddRequest;
import com.wanmi.sbc.empower.api.response.ledgercontent.LedgerContentAddResponse;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentModifyRequest;
import com.wanmi.sbc.empower.api.response.ledgercontent.LedgerContentModifyResponse;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentDelByIdRequest;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentDelByIdListRequest;
import com.wanmi.sbc.empower.ledgercontent.service.LedgerContentService;
import com.wanmi.sbc.empower.ledgercontent.model.root.LedgerContent;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>拉卡拉经营内容表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@RestController
@Validated
public class LedgerContentController implements LedgerContentProvider {
	@Autowired
	private LedgerContentService ledgerContentService;

	@Override
	public BaseResponse<LedgerContentAddResponse> add(@RequestBody @Valid LedgerContentAddRequest ledgerContentAddRequest) {
		LedgerContent ledgerContent = KsBeanUtil.convert(ledgerContentAddRequest, LedgerContent.class);
		return BaseResponse.success(new LedgerContentAddResponse(
				ledgerContentService.wrapperVo(ledgerContentService.add(ledgerContent))));
	}

	@Override
	public BaseResponse<LedgerContentModifyResponse> modify(@RequestBody @Valid LedgerContentModifyRequest ledgerContentModifyRequest) {
		LedgerContent ledgerContent = KsBeanUtil.convert(ledgerContentModifyRequest, LedgerContent.class);
		return BaseResponse.success(new LedgerContentModifyResponse(
				ledgerContentService.wrapperVo(ledgerContentService.modify(ledgerContent))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid LedgerContentDelByIdRequest ledgerContentDelByIdRequest) {
		LedgerContent ledgerContent = KsBeanUtil.convert(ledgerContentDelByIdRequest, LedgerContent.class);
		ledgerContent.setDelFlag(DeleteFlag.YES);
		ledgerContentService.deleteById(ledgerContent);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid LedgerContentDelByIdListRequest ledgerContentDelByIdListRequest) {
		ledgerContentService.deleteByIdList(ledgerContentDelByIdListRequest.getContentIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

