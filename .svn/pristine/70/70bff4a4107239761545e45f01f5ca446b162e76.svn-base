package com.wanmi.sbc.setting.provider.impl.appexternalconfig;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.appexternalconfig.AppExternalConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigPageRequest;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.appexternalconfig.AppExternalConfigPageResponse;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigListRequest;
import com.wanmi.sbc.setting.api.response.appexternalconfig.AppExternalConfigListResponse;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigByIdRequest;
import com.wanmi.sbc.setting.api.response.appexternalconfig.AppExternalConfigByIdResponse;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigExportRequest;
import com.wanmi.sbc.setting.bean.vo.AppExternalConfigVO;
import com.wanmi.sbc.setting.appexternalconfig.service.AppExternalConfigService;
import com.wanmi.sbc.setting.appexternalconfig.model.root.AppExternalConfig;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>AppExternalConfig查询服务接口实现</p>
 * @author 黄昭
 * @date 2022-09-27 15:26:05
 */
@RestController
@Validated
public class AppExternalConfigQueryController implements AppExternalConfigQueryProvider {
	@Autowired
	private AppExternalConfigService appExternalConfigService;

	@Override
	public BaseResponse<AppExternalConfigPageResponse> page(@RequestBody @Valid AppExternalConfigPageRequest appExternalConfigPageReq) {
		AppExternalConfigQueryRequest queryReq = KsBeanUtil.convert(appExternalConfigPageReq, AppExternalConfigQueryRequest.class);
		Page<AppExternalConfig> appExternalConfigPage = appExternalConfigService.page(queryReq);
		Page<AppExternalConfigVO> newPage = appExternalConfigPage.map(entity -> appExternalConfigService.wrapperVo(entity));
		MicroServicePage<AppExternalConfigVO> microPage = new MicroServicePage<>(newPage, appExternalConfigPageReq.getPageable());
		microPage.setTotal(appExternalConfigService.getPageTotal(queryReq));
		AppExternalConfigPageResponse finalRes = new AppExternalConfigPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<AppExternalConfigListResponse> list(@RequestBody @Valid AppExternalConfigListRequest appExternalConfigListReq) {
		AppExternalConfigQueryRequest queryReq = KsBeanUtil.convert(appExternalConfigListReq, AppExternalConfigQueryRequest.class);
		List<AppExternalConfig> appExternalConfigList = appExternalConfigService.list(queryReq);
		List<AppExternalConfigVO> newList = appExternalConfigList.stream().map(entity -> appExternalConfigService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new AppExternalConfigListResponse(newList));
	}

	@Override
	public BaseResponse<AppExternalConfigByIdResponse> getById(@RequestBody @Valid AppExternalConfigByIdRequest appExternalConfigByIdRequest) {
		AppExternalConfig appExternalConfig =
		appExternalConfigService.getOne(appExternalConfigByIdRequest.getId());
		return BaseResponse.success(new AppExternalConfigByIdResponse(appExternalConfigService.wrapperVo(appExternalConfig)));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid AppExternalConfigExportRequest request) {
		AppExternalConfigQueryRequest queryReq = KsBeanUtil.convert(request, AppExternalConfigQueryRequest.class);
		Long total = appExternalConfigService.count(queryReq);
		return BaseResponse.success(total);
	}

}

