package com.wanmi.sbc.setting.api.provider.appexternalconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigAddRequest;
import com.wanmi.sbc.setting.api.response.appexternalconfig.AppExternalConfigAddResponse;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigModifyRequest;
import com.wanmi.sbc.setting.api.response.appexternalconfig.AppExternalConfigModifyResponse;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigDelByIdRequest;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>AppExternalConfig保存服务Provider</p>
 * @author 黄昭
 * @date 2022-09-27 15:26:05
 */
@FeignClient(value = "${application.setting.name}", contextId = "AppExternalConfigProvider")
public interface AppExternalConfigProvider {

	/**
	 * 新增AppExternalConfigAPI
	 *
	 * @author 黄昭
	 * @param appExternalConfigAddRequest AppExternalConfig新增参数结构 {@link AppExternalConfigAddRequest}
	 * @return 新增的AppExternalConfig信息 {@link AppExternalConfigAddResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternalconfig/add")
	BaseResponse<AppExternalConfigAddResponse> add(@RequestBody @Valid AppExternalConfigAddRequest appExternalConfigAddRequest);

	/**
	 * 修改AppExternalConfigAPI
	 *
	 * @author 黄昭
	 * @param appExternalConfigModifyRequest AppExternalConfig修改参数结构 {@link AppExternalConfigModifyRequest}
	 * @return 修改的AppExternalConfig信息 {@link AppExternalConfigModifyResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternalconfig/modify")
	BaseResponse<AppExternalConfigModifyResponse> modify(@RequestBody @Valid AppExternalConfigModifyRequest appExternalConfigModifyRequest);

	/**
	 * 单个删除AppExternalConfigAPI
	 *
	 * @author 黄昭
	 * @param appExternalConfigDelByIdRequest 单个删除参数结构 {@link AppExternalConfigDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternalconfig/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid AppExternalConfigDelByIdRequest appExternalConfigDelByIdRequest);

}

