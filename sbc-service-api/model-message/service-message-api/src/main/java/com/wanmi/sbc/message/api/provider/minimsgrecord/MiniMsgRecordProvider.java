package com.wanmi.sbc.message.api.provider.minimsgrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.minimsgrecord.MiniMsgRecordModifyRequest;
import com.wanmi.sbc.message.api.response.minimsgrecord.MiniMsgRecordModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>小程序订阅消息配置表保存服务Provider</p>
 * @author xufeng
 * @date 2022-08-08 16:51:37
 */
@FeignClient(value = "${application.message.name}", contextId = "MiniMsgRecordProvider")
public interface MiniMsgRecordProvider {

	/**
	 * 修改小程序订阅消息配置表API
	 *
	 * @author xufeng
	 * @param miniMsgRecordModifyRequest 小程序订阅消息配置表修改参数结构 {@link MiniMsgRecordModifyRequest}
	 * @return 修改的小程序订阅消息配置表信息 {@link MiniMsgRecordModifyResponse}
	 */
	@PostMapping("/sms/${application.sms.version}/minimsgrecord/modify")
	BaseResponse modify(@RequestBody @Valid MiniMsgRecordModifyRequest miniMsgRecordModifyRequest);

}

