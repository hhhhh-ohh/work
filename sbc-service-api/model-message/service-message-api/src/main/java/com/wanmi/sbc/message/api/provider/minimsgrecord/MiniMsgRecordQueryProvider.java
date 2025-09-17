package com.wanmi.sbc.message.api.provider.minimsgrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.minimsgrecord.MiniMsgRecordByIdRequest;
import com.wanmi.sbc.message.api.response.minimsgrecord.MiniMsgRecordByIdResponse;
import com.wanmi.sbc.message.api.response.minimsgrecord.MiniMsgByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>小程序订阅消息配置表查询服务Provider</p>
 * @author xufeng
 * @date 2022-08-08 16:51:37
 */
@FeignClient(value = "${application.message.name}", contextId = "MiniMsgRecordQueryProvider")
public interface MiniMsgRecordQueryProvider {

	/**
	 * 单个查询小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param miniMsgRecordByIdRequest 单个查询小程序订阅消息配置表请求参数 {@link MiniMsgRecordByIdRequest}
	 * @return 小程序订阅消息配置表详情 {@link MiniMsgRecordByIdResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgrecord/get-by-id")
	BaseResponse<MiniMsgRecordByIdResponse> getById(@RequestBody @Valid MiniMsgRecordByIdRequest miniMsgRecordByIdRequest);

	/**
	 * 查询是否满足微信弹窗
	 *
	 * @author xufeng
	 * @param miniMsgRecordByIdRequest 查询是否满足微信弹窗 {@link MiniMsgRecordByIdRequest}
	 * @return 查询是否满足微信弹窗 {@link Boolean}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgrecord/pull-wx-page")
	BaseResponse<MiniMsgByIdResponse> pullWxPage(@RequestBody @Valid MiniMsgRecordByIdRequest miniMsgRecordByIdRequest);
}

