package com.wanmi.sbc.message.api.provider.vopmessage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.vopmessage.VopLogAddRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>Vop消息发送表保存服务Provider</p>
 * @author xufeng
 * @date 2022-05-20 10:53:00
 */
@FeignClient(value = "${application.message.name}", contextId = "VopLogProvider")
public interface VopLogProvider {

	/**
	 * 新增Vop消息发送表API
	 *
	 * @author xufeng
	 * @param vopLogAddRequest Vop消息发送表新增参数结构 {@link VopLogAddRequest}
	 * @return 新增的Vop消息发送表信息
	 */
	@PostMapping("/sms/${application.sms.version}/voplog/add")
    BaseResponse add(@RequestBody @Valid VopLogAddRequest vopLogAddRequest);

}

