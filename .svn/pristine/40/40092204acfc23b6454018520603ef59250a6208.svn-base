package com.wanmi.sbc.message.api.provider.minimsgsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingModifyRequest;
import com.wanmi.sbc.message.api.response.minimsgsetting.MiniMsgSettingModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>小程序订阅消息配置表保存服务Provider</p>
 * @author xufeng
 * @date 2022-08-08 11:37:13
 */
@FeignClient(value = "${application.message.name}", contextId = "MiniMsgSettingProvider")
public interface MiniMsgSettingProvider {

	/**
	 * 修改小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param miniMsgSettingModifyRequest 小程序订阅消息配置表修改参数结构 {@link MiniMsgSettingModifyRequest}
	 * @return 修改的小程序订阅消息配置表信息 {@link MiniMsgSettingModifyResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgmessagesetting/modify")
	BaseResponse modify(@RequestBody @Valid MiniMsgSettingModifyRequest miniMsgSettingModifyRequest);

}

