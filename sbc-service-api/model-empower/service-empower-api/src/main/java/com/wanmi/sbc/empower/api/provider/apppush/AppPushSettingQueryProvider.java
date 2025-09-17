package com.wanmi.sbc.empower.api.provider.apppush;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingPageRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSettingPageResponse;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingListRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSettingListResponse;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSettingByIdRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSettingByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>消息推送配置查询服务Provider</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
@FeignClient(value = "${application.empower.name}", contextId = "AppPushSettingQueryProvider")
public interface AppPushSettingQueryProvider {

	/**
	 * 分页查询消息推送配置API
	 *
	 * @author 韩伟
	 * @param appPushSettingPageRequest 分页请求参数和筛选对象 {@link AppPushSettingPageRequest}
	 * @return 消息推送配置分页列表信息 {@link AppPushSettingPageResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/apppushsetting/page")
	BaseResponse<AppPushSettingPageResponse> page(@RequestBody @Valid AppPushSettingPageRequest appPushSettingPageRequest);

	/**
	 * 列表查询消息推送配置API
	 *
	 * @author 韩伟
	 * @param appPushSettingListRequest 列表请求参数和筛选对象 {@link AppPushSettingListRequest}
	 * @return 消息推送配置的列表信息 {@link AppPushSettingListResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/apppushsetting/list")
	BaseResponse<AppPushSettingListResponse> list(@RequestBody @Valid AppPushSettingListRequest appPushSettingListRequest);

	/**
	 * 单个查询消息推送配置API
	 *
	 * @author 韩伟
	 * @param appPushSettingByIdRequest 单个查询消息推送配置请求参数 {@link AppPushSettingByIdRequest}
	 * @return 消息推送配置详情 {@link AppPushSettingByIdResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/apppushsetting/get-by-id")
	BaseResponse<AppPushSettingByIdResponse> getById(@RequestBody @Valid AppPushSettingByIdRequest appPushSettingByIdRequest);

}

