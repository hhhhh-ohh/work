package com.wanmi.sbc.empower.api.provider.sellplatform.miniprogramsubscribe;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformInitMiniMsgTempRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformSendMiniMsgRequest;
import com.wanmi.sbc.empower.api.response.sellplatform.miniprogramsubscibe.PlatformMiniMsgTempResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @author xufeng
 * @className PlatformMiniMsgProvider
 * @description PlatformMiniMsgProvider
 * @date 2022/08/09 19:19
 */
@FeignClient(value = "${application.empower.name}", contextId = "PlatformMiniMsgProvider")
public interface PlatformMiniMsgProvider {

    /**
     * 初始化小程序订阅消息模板
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/platform/initMiniProgramSubscribeTemplate/")
    BaseResponse<List<PlatformMiniMsgTempResponse>> initMiniProgramSubscribeTemplate(@RequestBody @Valid PlatformInitMiniMsgTempRequest request);

    /**
     * 发送小程序订阅消息
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/platform/sendMiniProgramSubscribeMessage/")
    BaseResponse sendMiniProgramSubscribeMessage(@RequestBody @Valid PlatformSendMiniMsgRequest request);
}
