package com.wanmi.sbc.empower.api.provider.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelConfigByTypeRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelConfigListResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelConfigResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author wur
 * @className ChannelConfigQueryProvider
 * @description 渠道配置信息服务
 * @date 2021/5/20 10:28
 */
@FeignClient(value = "${application.empower.name}", contextId = "ChannelConfigQueryProvider")
public interface ChannelConfigQueryProvider {

    /**
     * 查询渠道配置API
     * @author  wur
     * @date: 2021/5/20 10:41
     * @return 渠道配置列表信息 {@link ChannelConfigListResponse}
     **/
    @PostMapping("/channel/${application.empower.version}/config/list")
    BaseResponse<ChannelConfigListResponse> list();

    /**
     * 获取渠道配置API
     * @author  wur
     * @date: 2021/5/20 10:41
     * @param request 请求参数 {@link ChannelConfigByTypeRequest}
     * @return 渠道配置信息 {@link ChannelConfigResponse}
     **/
    @PostMapping("/channel/${application.empower.version}/config/get-by-type")
    BaseResponse<ChannelConfigResponse> getByType(@RequestBody @Valid ChannelConfigByTypeRequest request);

}
