package com.wanmi.sbc.setting.provider.impl.appexternallink;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.appexternallink.AppExternalLinkQueryProvider;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkPageRequest;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkQueryRequest;
import com.wanmi.sbc.setting.api.response.appexternallink.AppExternalLinkPageResponse;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkListRequest;
import com.wanmi.sbc.setting.api.response.appexternallink.AppExternalLinkListResponse;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkByIdRequest;
import com.wanmi.sbc.setting.api.response.appexternallink.AppExternalLinkByIdResponse;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkExportRequest;
import com.wanmi.sbc.setting.bean.vo.AppExternalLinkVO;
import com.wanmi.sbc.setting.appexternallink.service.AppExternalLinkService;
import com.wanmi.sbc.setting.appexternallink.model.root.AppExternalLink;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>AppExternalLink查询服务接口实现</p>
 * @author 黄昭
 * @date 2022-09-28 14:16:09
 */
@RestController
@Validated
public class AppExternalLinkQueryController implements AppExternalLinkQueryProvider {
	@Autowired
	private AppExternalLinkService appExternalLinkService;

	@Override
	public BaseResponse<AppExternalLinkPageResponse> page(@RequestBody @Valid AppExternalLinkPageRequest appExternalLinkPageReq) {
		AppExternalLinkQueryRequest queryReq = KsBeanUtil.convert(appExternalLinkPageReq, AppExternalLinkQueryRequest.class);
		Page<AppExternalLink> appExternalLinkPage = appExternalLinkService.page(queryReq);
		Page<AppExternalLinkVO> newPage = appExternalLinkPage.map(entity -> appExternalLinkService.wrapperVo(entity));
		MicroServicePage<AppExternalLinkVO> microPage = new MicroServicePage<>(newPage, appExternalLinkPageReq.getPageable());
		AppExternalLinkPageResponse finalRes = new AppExternalLinkPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<AppExternalLinkListResponse> list(@RequestBody @Valid AppExternalLinkListRequest appExternalLinkListReq) {
		AppExternalLinkQueryRequest queryReq = KsBeanUtil.convert(appExternalLinkListReq, AppExternalLinkQueryRequest.class);
		List<AppExternalLink> appExternalLinkList = appExternalLinkService.list(queryReq);
		List<AppExternalLinkVO> newList = appExternalLinkList.stream().map(entity -> appExternalLinkService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new AppExternalLinkListResponse(newList));
	}

	@Override
	public BaseResponse<AppExternalLinkByIdResponse> getById(@RequestBody @Valid AppExternalLinkByIdRequest appExternalLinkByIdRequest) {
		AppExternalLink appExternalLink =
		appExternalLinkService.getOne(appExternalLinkByIdRequest.getId());
		return BaseResponse.success(new AppExternalLinkByIdResponse(appExternalLinkService.wrapperVo(appExternalLink)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid AppExternalLinkExportRequest request) {
		AppExternalLinkQueryRequest queryReq = KsBeanUtil.convert(request, AppExternalLinkQueryRequest.class);
		Long total = appExternalLinkService.count(queryReq);
		return BaseResponse.success(total);
	}
}

