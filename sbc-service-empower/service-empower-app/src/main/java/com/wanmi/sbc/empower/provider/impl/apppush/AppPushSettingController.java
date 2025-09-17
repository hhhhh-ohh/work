package com.wanmi.sbc.empower.provider.impl.apppush;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.empower.api.provider.apppush.AppPushSettingProvider;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingAddRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSettingAddResponse;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingModifyRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSettingModifyResponse;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingDelByIdRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingDelByIdListRequest;
import com.wanmi.sbc.empower.apppush.service.AppPushSettingService;
import com.wanmi.sbc.empower.apppush.model.root.AppPushSetting;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>消息推送配置保存服务接口实现</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
@RestController
@Validated
public class AppPushSettingController implements AppPushSettingProvider {
	@Autowired
	private AppPushSettingService appPushSettingService;

	@Override
	public BaseResponse<AppPushSettingAddResponse> add(@RequestBody @Valid AppPushSettingAddRequest appPushSettingAddRequest) {
		AppPushSetting appPushSetting = KsBeanUtil.convert(appPushSettingAddRequest, AppPushSetting.class);
		return BaseResponse.success(new AppPushSettingAddResponse(
				appPushSettingService.wrapperVo(appPushSettingService.add(appPushSetting))));
	}

	@Override
	public BaseResponse<AppPushSettingModifyResponse> modify(@RequestBody @Valid AppPushSettingModifyRequest appPushSettingModifyRequest) {
		AppPushSetting appPushSetting = KsBeanUtil.convert(appPushSettingModifyRequest, AppPushSetting.class);
		return BaseResponse.success(new AppPushSettingModifyResponse(
				appPushSettingService.wrapperVo(appPushSettingService.modify(appPushSetting))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid AppPushSettingDelByIdRequest appPushSettingDelByIdRequest) {
		AppPushSetting appPushSetting = KsBeanUtil.convert(appPushSettingDelByIdRequest, AppPushSetting.class);
		appPushSetting.setDelFlag(DeleteFlag.YES);
		appPushSettingService.deleteById(appPushSetting);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid AppPushSettingDelByIdListRequest appPushSettingDelByIdListRequest) {
		List<AppPushSetting> appPushSettingList = appPushSettingDelByIdListRequest.getIdList().stream()
			.map(Id -> {
				AppPushSetting appPushSetting = KsBeanUtil.convert(Id, AppPushSetting.class);
				appPushSetting.setDelFlag(DeleteFlag.YES);
				return appPushSetting;
			}).collect(Collectors.toList());
		appPushSettingService.deleteByIdList(appPushSettingList);
		return BaseResponse.SUCCESSFUL();
	}

}

