package com.wanmi.sbc.empower.provider.impl.apppush;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.apppush.AppPushSettingQueryProvider;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingPageRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingQueryRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSettingPageResponse;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingListRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSettingListResponse;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingByIdRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSettingByIdResponse;
import com.wanmi.sbc.empower.bean.vo.AppPushSettingVO;
import com.wanmi.sbc.empower.apppush.service.AppPushSettingService;
import com.wanmi.sbc.empower.apppush.model.root.AppPushSetting;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>消息推送配置查询服务接口实现</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
@RestController
@Validated
public class AppPushSettingQueryController implements AppPushSettingQueryProvider {
	@Autowired
	private AppPushSettingService appPushSettingService;

	@Override
	public BaseResponse<AppPushSettingPageResponse> page(@RequestBody @Valid AppPushSettingPageRequest appPushSettingPageRequest) {
		AppPushSettingQueryRequest queryReq = KsBeanUtil.convert(appPushSettingPageRequest, AppPushSettingQueryRequest.class);
		Page<AppPushSetting> appPushSettingPage = appPushSettingService.page(queryReq);
		Page<AppPushSettingVO> newPage = appPushSettingPage.map(entity -> appPushSettingService.wrapperVo(entity));
		MicroServicePage<AppPushSettingVO> microPage = new MicroServicePage<>(newPage, appPushSettingPageRequest.getPageable());
		AppPushSettingPageResponse finalRes = new AppPushSettingPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<AppPushSettingListResponse> list(@RequestBody @Valid AppPushSettingListRequest appPushSettingListRequest) {
		AppPushSettingQueryRequest queryReq = KsBeanUtil.convert(appPushSettingListRequest, AppPushSettingQueryRequest.class);
		List<AppPushSetting> appPushSettingList = appPushSettingService.list(queryReq);
		List<AppPushSettingVO> newList = appPushSettingList.stream().map(entity -> appPushSettingService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new AppPushSettingListResponse(newList));
	}

	@Override
	public BaseResponse<AppPushSettingByIdResponse> getById(@RequestBody @Valid AppPushSettingByIdRequest appPushSettingByIdRequest) {
		AppPushSetting appPushSetting =
		appPushSettingService.getOne(appPushSettingByIdRequest.getId());
		return BaseResponse.success(new AppPushSettingByIdResponse(appPushSettingService.wrapperVo(appPushSetting)));
	}

}

