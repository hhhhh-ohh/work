package com.wanmi.sbc.message.api.provider.minimsgsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingByNodeIdRequest;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingListRequest;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingPageRequest;
import com.wanmi.sbc.message.api.response.minimsgsetting.MiniMsgSettingByIdResponse;
import com.wanmi.sbc.message.api.response.minimsgsetting.MiniMsgSettingListResponse;
import com.wanmi.sbc.message.api.response.minimsgsetting.MiniMsgSettingPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>小程序订阅消息配置表查询服务Provider</p>
 * @author xufeng
 * @date 2022-08-08 11:37:13
 */
@FeignClient(value = "${application.message.name}", contextId = "MiniMsgSettingQueryProvider")
public interface MiniMsgSettingQueryProvider {

	/**
	 * 分页查询小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param minimsgsettingPageReq 分页请求参数和筛选对象 {@link MiniMsgSettingPageRequest}
	 * @return 小程序订阅消息配置表分页列表信息 {@link MiniMsgSettingPageResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgsetting/page")
	BaseResponse<MiniMsgSettingPageResponse> page(@RequestBody @Valid MiniMsgSettingPageRequest minimsgsettingPageReq);

	/**
	 * 列表查询小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param minimsgsettingListReq 列表请求参数和筛选对象 {@link MiniMsgSettingListRequest}
	 * @return 小程序订阅消息配置表的列表信息 {@link MiniMsgSettingListResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgsetting/list")
	BaseResponse<MiniMsgSettingListResponse> list(@RequestBody @Valid MiniMsgSettingListRequest minimsgsettingListReq);

	/**
	 * 单个查询小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param miniMsgSettingByIdRequest 单个查询小程序订阅消息配置表请求参数 {@link MiniMsgSettingByIdRequest}
	 * @return 小程序订阅消息配置表详情 {@link MiniMsgSettingByIdResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgsetting/get-by-id")
	BaseResponse<MiniMsgSettingByIdResponse> getById(@RequestBody @Valid MiniMsgSettingByIdRequest miniMsgSettingByIdRequest);

	/**
	 * 单个查询小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param miniMsgSettingByNodeIdRequest 单个查询小程序订阅消息配置表请求参数 {@link MiniMsgSettingByNodeIdRequest}
	 * @return 小程序订阅消息配置表详情 {@link MiniMsgSettingByIdResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgsetting/get-by-nodeId")
	BaseResponse<MiniMsgSettingByIdResponse> findByNodeId(@RequestBody @Valid MiniMsgSettingByNodeIdRequest miniMsgSettingByNodeIdRequest);
}

