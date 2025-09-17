package com.wanmi.sbc.empower.provider.impl.ledgermcc;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.ledgermcc.LedgerMccQueryProvider;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccPageRequest;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccQueryRequest;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccPageResponse;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccListRequest;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccListResponse;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccByIdRequest;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccByIdResponse;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccExportRequest;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccExportResponse;
import com.wanmi.sbc.empower.bean.vo.LedgerMccVO;
import com.wanmi.sbc.empower.bean.vo.LedgerMccPageVO;
import com.wanmi.sbc.empower.ledgermcc.service.LedgerMccService;
import com.wanmi.sbc.empower.ledgermcc.model.root.LedgerMcc;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>拉卡拉mcc表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@RestController
@Validated
public class LedgerMccQueryController implements LedgerMccQueryProvider {
	@Autowired
	private LedgerMccService ledgerMccService;

	@Override
	public BaseResponse<LedgerMccPageResponse> page(@RequestBody @Valid LedgerMccPageRequest ledgerMccPageReq) {
		LedgerMccQueryRequest queryReq = KsBeanUtil.convert(ledgerMccPageReq, LedgerMccQueryRequest.class);
		Page<LedgerMcc> ledgerMccPage = ledgerMccService.page(queryReq);
		Page<LedgerMccVO> newPage = ledgerMccPage.map(entity -> ledgerMccService.wrapperVo(entity));
		MicroServicePage<LedgerMccVO> microPage = new MicroServicePage<>(newPage, ledgerMccPageReq.getPageable());
		LedgerMccPageResponse finalRes = new LedgerMccPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<LedgerMccListResponse> list(@RequestBody @Valid LedgerMccListRequest ledgerMccListReq) {
		LedgerMccQueryRequest queryReq = KsBeanUtil.convert(ledgerMccListReq, LedgerMccQueryRequest.class);
		List<LedgerMcc> ledgerMccList = ledgerMccService.list(queryReq);
		List<LedgerMccVO> newList = ledgerMccList.stream().map(entity -> ledgerMccService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new LedgerMccListResponse(newList));
	}

	@Override
	public BaseResponse<LedgerMccByIdResponse> getById(@RequestBody @Valid LedgerMccByIdRequest ledgerMccByIdRequest) {
		LedgerMcc ledgerMcc =
		ledgerMccService.getOne(ledgerMccByIdRequest.getMccId());
		return BaseResponse.success(new LedgerMccByIdResponse(ledgerMccService.wrapperVo(ledgerMcc)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid LedgerMccExportRequest request) {
		LedgerMccQueryRequest queryReq = KsBeanUtil.convert(request, LedgerMccQueryRequest.class);
		Long total = ledgerMccService.count(queryReq);
		return BaseResponse.success(total);
	}

}

