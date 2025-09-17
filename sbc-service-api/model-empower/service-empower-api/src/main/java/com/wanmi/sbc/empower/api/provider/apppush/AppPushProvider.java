package com.wanmi.sbc.empower.api.provider.apppush;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.apppush.AppPushCancelRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushQueryRequest;
import com.wanmi.sbc.empower.api.request.apppush.AppPushSendRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushCancelResponse;
import com.wanmi.sbc.empower.api.response.apppush.AppPushQueryResponse;
import com.wanmi.sbc.empower.api.response.apppush.AppPushSendResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>消息推送服务Provider</p>
 * @author 韩伟
 * @date 2021-04-01
 */
@FeignClient(value = "${application.empower.name}", contextId = "AppPushProvider")
public interface AppPushProvider {

	/**
	 * 消息发送
	 * @param request
	 * @return
	 */
	@PostMapping("/empower/${application.empower.version}/apppush/send")
	BaseResponse<AppPushSendResponse> send(@RequestBody @Valid AppPushSendRequest request);

	/**
	 * 任务类消息状态查询
	 * @param request
	 * @return
	 */
	@PostMapping("/empower/${application.empower.version}/apppush/query")
	BaseResponse<AppPushQueryResponse> query(@RequestBody @Valid AppPushQueryRequest request);

	/**
	 * 任务类消息取消
	 * @param request
	 * @return
	 */
	@PostMapping("/empower/${application.empower.version}/apppush/cancel")
	BaseResponse<AppPushCancelResponse> cancel(@RequestBody @Valid AppPushCancelRequest request);

}

