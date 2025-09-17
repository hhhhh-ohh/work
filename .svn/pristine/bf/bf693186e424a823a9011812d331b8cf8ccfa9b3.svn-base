package com.wanmi.sbc.empower.api.provider.apppush;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingAddRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSettingAddResponse;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingModifyRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSettingModifyResponse;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingDelByIdRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>消息推送配置保存服务Provider</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
@FeignClient(value = "${application.empower.name}", contextId = "AppPushSettingProvider")
public interface AppPushSettingProvider {

	/**
	 * 新增消息推送配置API
	 *
	 * @author 韩伟
	 * @param appPushSettingAddRequest 消息推送配置新增参数结构 {@link AppPushSettingAddRequest}
	 * @return 新增的消息推送配置信息 {@link AppPushSettingAddResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/apppushsetting/add")
	BaseResponse<AppPushSettingAddResponse> add(@RequestBody @Valid AppPushSettingAddRequest appPushSettingAddRequest);

	/**
	 * 修改消息推送配置API
	 *
	 * @author 韩伟
	 * @param appPushSettingModifyRequest 消息推送配置修改参数结构 {@link AppPushSettingModifyRequest}
	 * @return 修改的消息推送配置信息 {@link AppPushSettingModifyResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/apppushsetting/modify")
	BaseResponse<AppPushSettingModifyResponse> modify(@RequestBody @Valid AppPushSettingModifyRequest appPushSettingModifyRequest);

	/**
	 * 单个删除消息推送配置API
	 *
	 * @author 韩伟
	 * @param appPushSettingDelByIdRequest 单个删除参数结构 {@link AppPushSettingDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/apppushsetting/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid AppPushSettingDelByIdRequest appPushSettingDelByIdRequest);

	/**
	 * 批量删除消息推送配置API
	 *
	 * @author 韩伟
	 * @param appPushSettingDelByIdListRequest 批量删除参数结构 {@link AppPushSettingDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/apppushsetting/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid AppPushSettingDelByIdListRequest appPushSettingDelByIdListRequest);

}

