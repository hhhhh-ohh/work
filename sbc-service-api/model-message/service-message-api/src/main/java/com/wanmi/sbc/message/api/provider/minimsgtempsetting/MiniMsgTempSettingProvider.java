package com.wanmi.sbc.message.api.provider.minimsgtempsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingBatchModifyRequest;
import com.wanmi.sbc.message.api.request.minimsgtempsetting.MiniMsgTempSettingModifyRequest;
import com.wanmi.sbc.message.api.response.minimsgtempsetting.MiniMsgTempSettingModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>小程序订阅消息模版配置表保存服务Provider</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@FeignClient(value = "${application.message.name}", contextId = "MiniMsgTempSettingProvider")
public interface MiniMsgTempSettingProvider {

	/**
	 * 修改小程序订阅消息模版配置表API
	 *
	 * @author xufeng
	 * @param miniMsgTempSettingModifyRequest 小程序订阅消息模版配置表修改参数结构 {@link MiniMsgTempSettingModifyRequest}
	 * @return 修改的小程序订阅消息模版配置表信息 {@link MiniMsgTempSettingModifyResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgtempsetting/modify")
	BaseResponse<MiniMsgTempSettingModifyResponse> modify(@RequestBody @Valid MiniMsgTempSettingModifyRequest miniMsgTempSettingModifyRequest);

	/**
	 * 修改小程序订阅消息模版配置表API
	 *
	 * @author xufeng
	 * @param miniMsgTempSettingBatchModifyRequest 小程序订阅消息模版配置表修改参数结构 {@link MiniMsgTempSettingBatchModifyRequest}
	 * @return 修改的小程序订阅消息模版配置表信息 {@link BaseResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgtempsetting/batchModify")
	BaseResponse batchModify(@RequestBody @Valid MiniMsgTempSettingBatchModifyRequest miniMsgTempSettingBatchModifyRequest);
}

