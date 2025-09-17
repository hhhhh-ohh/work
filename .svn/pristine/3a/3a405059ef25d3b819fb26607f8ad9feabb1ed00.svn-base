package com.wanmi.sbc.empower.provider.impl.apppush;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.empower.api.provider.apppush.AppPushProvider;
import com.wanmi.sbc.empower.api.request.apppush.AppPushCancelRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushQueryRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSendRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushCancelResponse;
import com.wanmi.sbc.empower.api.response.apppush.AppPushQueryResponse;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSendResponse;
import com.wanmi.sbc.empower.apppush.service.AppPushBaseService;
import com.wanmi.sbc.empower.apppush.service.AppPushServiceFactory;
import com.wanmi.sbc.empower.apppush.service.AppPushSettingService;
import com.wanmi.sbc.empower.bean.constant.AppPushErrorCode;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.vo.AppPushSettingVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>消息推送服务接口实现</p>
 * @author 韩伟
 * @date 2021-04-02
 */
@RestController
@Validated
public class AppPushController implements AppPushProvider {

	@Autowired
	private AppPushSettingService appPushSettingService;
	@Autowired
	private AppPushServiceFactory appPushServiceFactory;

	@Override
	public BaseResponse<AppPushSendResponse> send(@Valid AppPushSendRequest request) {
		if(CollectionUtils.isEmpty(request.getAndroidTokenList()) && CollectionUtils.isEmpty(request.getIosTokenList())){
			throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060019);
		}
		AppPushSettingVO appPushSetting = appPushSettingService.getAvailable();
		AppPushBaseService appPushBaseService = appPushServiceFactory.create(appPushSetting.getPlatformType());
		return appPushBaseService.send(request);
	}

	@Override
	public BaseResponse<AppPushQueryResponse> query(@Valid AppPushQueryRequest request) {
		AppPushSettingVO appPushSetting = appPushSettingService.getAvailable();
		AppPushBaseService appPushBaseService = appPushServiceFactory.create(appPushSetting.getPlatformType());
		return appPushBaseService.query(request);
	}

	@Override
	public BaseResponse<AppPushCancelResponse> cancel(@Valid AppPushCancelRequest request) {
		AppPushSettingVO appPushSetting = appPushSettingService.getAvailable();
		AppPushBaseService appPushBaseService = appPushServiceFactory.create(appPushSetting.getPlatformType());
		return appPushBaseService.cancel(request);
	}
}

