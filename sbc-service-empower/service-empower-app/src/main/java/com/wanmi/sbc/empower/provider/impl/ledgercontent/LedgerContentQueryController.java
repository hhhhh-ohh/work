package com.wanmi.sbc.empower.provider.impl.ledgercontent;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.ledgercontent.LedgerContentQueryProvider;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentPageRequest;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentQueryRequest;
import com.wanmi.sbc.empower.api.response.ledgercontent.LedgerContentPageResponse;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentListRequest;
import com.wanmi.sbc.empower.api.response.ledgercontent.LedgerContentListResponse;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentByIdRequest;
import com.wanmi.sbc.empower.api.response.ledgercontent.LedgerContentByIdResponse;
import com.wanmi.sbc.empower.api.request.ledgercontent.LedgerContentExportRequest;
import com.wanmi.sbc.empower.api.response.ledgercontent.LedgerContentExportResponse;
import com.wanmi.sbc.empower.bean.vo.LedgerContentVO;
import com.wanmi.sbc.empower.bean.vo.LedgerContentPageVO;
import com.wanmi.sbc.empower.ledgercontent.service.LedgerContentService;
import com.wanmi.sbc.empower.ledgercontent.model.root.LedgerContent;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>拉卡拉经营内容表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@RestController
@Validated
public class LedgerContentQueryController implements LedgerContentQueryProvider {
	@Autowired
	private LedgerContentService ledgerContentService;

	@Override
	public BaseResponse<LedgerContentPageResponse> page(@RequestBody @Valid LedgerContentPageRequest ledgerContentPageReq) {
		LedgerContentQueryRequest queryReq = KsBeanUtil.convert(ledgerContentPageReq, LedgerContentQueryRequest.class);
		Page<LedgerContent> ledgerContentPage = ledgerContentService.page(queryReq);
		Page<LedgerContentVO> newPage = ledgerContentPage.map(entity -> ledgerContentService.wrapperVo(entity));
		MicroServicePage<LedgerContentVO> microPage = new MicroServicePage<>(newPage, ledgerContentPageReq.getPageable());
		LedgerContentPageResponse finalRes = new LedgerContentPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<LedgerContentListResponse> list(@RequestBody @Valid LedgerContentListRequest ledgerContentListReq) {
		LedgerContentQueryRequest queryReq = KsBeanUtil.convert(ledgerContentListReq, LedgerContentQueryRequest.class);
		List<LedgerContent> ledgerContentList = ledgerContentService.list(queryReq);
		List<LedgerContentVO> newList = ledgerContentList.stream().map(entity -> ledgerContentService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new LedgerContentListResponse(newList));
	}

	@Override
	public BaseResponse<LedgerContentByIdResponse> getById(@RequestBody @Valid LedgerContentByIdRequest ledgerContentByIdRequest) {
		LedgerContent ledgerContent =
		ledgerContentService.getOne(ledgerContentByIdRequest.getContentId());
		return BaseResponse.success(new LedgerContentByIdResponse(ledgerContentService.wrapperVo(ledgerContent)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid LedgerContentExportRequest request) {
		LedgerContentQueryRequest queryReq = KsBeanUtil.convert(request, LedgerContentQueryRequest.class);
		Long total = ledgerContentService.count(queryReq);
		return BaseResponse.success(total);
	}

}

