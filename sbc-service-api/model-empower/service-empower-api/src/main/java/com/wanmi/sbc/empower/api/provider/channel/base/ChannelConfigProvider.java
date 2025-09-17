package com.wanmi.sbc.empower.api.provider.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelConfigModifyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author wur
 * @className ChannelConfigProvider
 * @description 渠道配置信息服务
 * @date 2021/5/20 10:28
 */
@FeignClient(value = "${application.empower.name}", contextId = "ChannelConfigProvider")
public interface ChannelConfigProvider {

    /**
     * 修改第三方平台修改API
     *
     * @author dyt
     * @param request 第三方平台配置修改参数结构 {@link ChannelConfigModifyRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/channel/${application.empower.version}/config/modify")
    BaseResponse modify(@RequestBody @Valid ChannelConfigModifyRequest request);

}
