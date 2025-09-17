package com.wanmi.sbc.setting.provider.impl.appexternalconfig;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.provider.appexternalconfig.AppExternalConfigProvider;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigAddRequest;
import com.wanmi.sbc.setting.api.response.appexternalconfig.AppExternalConfigAddResponse;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigModifyRequest;
import com.wanmi.sbc.setting.api.response.appexternalconfig.AppExternalConfigModifyResponse;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigDelByIdRequest;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigDelByIdListRequest;
import com.wanmi.sbc.setting.appexternalconfig.service.AppExternalConfigService;
import com.wanmi.sbc.setting.appexternalconfig.model.root.AppExternalConfig;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>AppExternalConfig保存服务接口实现</p>
 * @author 黄昭
 * @date 2022-09-27 15:26:05
 */
@RestController
@Validated
public class AppExternalConfigController implements AppExternalConfigProvider {
	@Autowired
	private AppExternalConfigService appExternalConfigService;

	@Override
	public BaseResponse<AppExternalConfigAddResponse> add(@RequestBody @Valid AppExternalConfigAddRequest appExternalConfigAddRequest) {
		return BaseResponse.success(new AppExternalConfigAddResponse(appExternalConfigService
				.wrapperVo(appExternalConfigService.add(appExternalConfigAddRequest))));
	}

	@Override
	public BaseResponse<AppExternalConfigModifyResponse> modify(@RequestBody @Valid AppExternalConfigModifyRequest appExternalConfigModifyRequest) {
		return BaseResponse.success(new AppExternalConfigModifyResponse(appExternalConfigService
				.wrapperVo(appExternalConfigService.modify(appExternalConfigModifyRequest))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid AppExternalConfigDelByIdRequest appExternalConfigDelByIdRequest) {
		appExternalConfigService.deleteById(appExternalConfigDelByIdRequest);
		return BaseResponse.SUCCESSFUL();
	}

}

