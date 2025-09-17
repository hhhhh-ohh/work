package com.wanmi.sbc.message.api.provider.minimsgactivitysetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingPageRequest;
import com.wanmi.sbc.message.api.request.minimsgactivitysetting.MiniMsgActivitySettingQueryRequest;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingByIdResponse;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingListResponse;
import com.wanmi.sbc.message.api.response.minimsgactivitysetting.MiniMsgActivitySettingPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>小程序订阅消息配置表查询服务Provider</p>
 * @author xufeng
 * @date 2022-08-11 16:16:32
 */
@FeignClient(value = "${application.message.name}", contextId = "MiniMsgActivitySettingQueryProvider")
public interface MiniMsgActivitySettingQueryProvider {

	/**
	 * 分页查询小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param miniMsgActivitySettingPageRequest 分页请求参数和筛选对象 {@link MiniMsgActivitySettingPageRequest}
	 * @return 小程序订阅消息配置表分页列表信息 {@link MiniMsgActivitySettingPageResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgactivitysetting/page")
	BaseResponse<MiniMsgActivitySettingPageResponse> page(@RequestBody @Valid MiniMsgActivitySettingPageRequest miniMsgActivitySettingPageRequest);

	/**
	 * 单个查询小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param miniMsgActivitySettingByIdRequest 单个查询小程序订阅消息配置表请求参数 {@link MiniMsgActivitySettingByIdRequest}
	 * @return 小程序订阅消息配置表详情 {@link MiniMsgActivitySettingByIdResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgactivitysetting/get-by-id")
	BaseResponse<MiniMsgActivitySettingByIdResponse> getById(@RequestBody @Valid MiniMsgActivitySettingByIdRequest miniMsgActivitySettingByIdRequest);

	/**
	 * 列表查询小程序订阅消息配置表API
	 * @param queryRequest
	 * @return
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgactivitysetting/list")
	BaseResponse<MiniMsgActivitySettingListResponse> list(@RequestBody @Valid MiniMsgActivitySettingQueryRequest queryRequest);

}

