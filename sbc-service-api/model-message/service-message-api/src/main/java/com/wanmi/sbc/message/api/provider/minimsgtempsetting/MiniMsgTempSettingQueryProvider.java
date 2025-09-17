package com.wanmi.sbc.message.api.provider.minimsgtempsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingByIdRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingByNodeIdRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingListRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingPageRequest;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingByIdResponse;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingListResponse;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>小程序订阅消息模版配置表查询服务Provider</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@FeignClient(value = "${application.message.name}", contextId = "MiniMsgTempSettingQueryProvider")
public interface MiniMsgTempSettingQueryProvider {

	/**
	 * 分页查询小程序订阅消息模版配置表API
	 *
	 * @author xufeng
	 * @param minimsgtempsettingPageReq 分页请求参数和筛选对象 {@link MiniMsgTempSettingPageRequest}
	 * @return 小程序订阅消息模版配置表分页列表信息 {@link MiniMsgTempSettingPageResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgtempsetting/page")
	BaseResponse<MiniMsgTempSettingPageResponse> page(@RequestBody @Valid MiniMsgTempSettingPageRequest minimsgtempsettingPageReq);

	/**
	 * 列表查询小程序订阅消息模版配置表API
	 *
	 * @author xufeng
	 * @param minimsgtempsettingListReq 列表请求参数和筛选对象 {@link MiniMsgTempSettingListRequest}
	 * @return 小程序订阅消息模版配置表的列表信息 {@link MiniMsgTempSettingListResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgtempsetting/list")
	BaseResponse<MiniMsgTempSettingListResponse> list(@RequestBody @Valid MiniMsgTempSettingListRequest minimsgtempsettingListReq);

	/**
	 * 单个查询小程序订阅消息模版配置表API
	 *
	 * @author xufeng
	 * @param miniMsgTempSettingByIdRequest 单个查询小程序订阅消息模版配置表请求参数 {@link MiniMsgTempSettingByIdRequest}
	 * @return 小程序订阅消息模版配置表详情 {@link MiniMsgTempSettingByIdResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgtempsetting/get-by-id")
	BaseResponse<MiniMsgTempSettingByIdResponse> getById(@RequestBody @Valid MiniMsgTempSettingByIdRequest miniMsgTempSettingByIdRequest);

	/**
	 * 单个查询小程序订阅消息模版配置表API
	 *
	 * @author xufeng
	 * @param templateSettingByTriggerNodeIdRequest 单个查询小程序订阅消息模版配置表请求参数 {@link MiniMsgTempSettingByNodeIdRequest}
	 * @return 小程序订阅消息模版配置表详情 {@link MiniMsgTempSettingByIdResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgtempsetting/get-by-triggernodeid")
	BaseResponse<MiniMsgTempSettingByIdResponse> findByTriggerNodeId(@RequestBody @Valid MiniMsgTempSettingByNodeIdRequest templateSettingByTriggerNodeIdRequest);

}

