package com.wanmi.sbc.setting.provider.impl.appexternallink;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.provider.appexternallink.AppExternalLinkProvider;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkAddRequest;
import com.wanmi.sbc.setting.api.response.appexternallink.AppExternalLinkAddResponse;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkModifyRequest;
import com.wanmi.sbc.setting.api.response.appexternallink.AppExternalLinkModifyResponse;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkDelByIdRequest;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkDelByIdListRequest;
import com.wanmi.sbc.setting.appexternallink.service.AppExternalLinkService;
import com.wanmi.sbc.setting.appexternallink.model.root.AppExternalLink;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>AppExternalLink保存服务接口实现</p>
 * @author 黄昭
 * @date 2022-09-28 14:16:09
 */
@RestController
@Validated
public class AppExternalLinkController implements AppExternalLinkProvider {
	@Autowired
	private AppExternalLinkService appExternalLinkService;

	@Override
	public BaseResponse<AppExternalLinkAddResponse> add(@RequestBody @Valid AppExternalLinkAddRequest appExternalLinkAddRequest) {
		return BaseResponse.success(new AppExternalLinkAddResponse(
				appExternalLinkService.wrapperVo(appExternalLinkService.add(appExternalLinkAddRequest))));
	}

	@Override
	public BaseResponse<AppExternalLinkModifyResponse> modify(@RequestBody @Valid AppExternalLinkModifyRequest appExternalLinkModifyRequest) {
		return BaseResponse.success(new AppExternalLinkModifyResponse(
				appExternalLinkService.wrapperVo(appExternalLinkService.modify(appExternalLinkModifyRequest))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid AppExternalLinkDelByIdRequest appExternalLinkDelByIdRequest) {
		appExternalLinkService.deleteById(appExternalLinkDelByIdRequest);
		return BaseResponse.SUCCESSFUL();
	}

}

