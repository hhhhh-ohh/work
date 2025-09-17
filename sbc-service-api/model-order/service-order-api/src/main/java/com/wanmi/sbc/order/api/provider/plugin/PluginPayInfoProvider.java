package com.wanmi.sbc.order.api.provider.plugin;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.plugin.PluginPayInfoAddRequest;
import com.wanmi.sbc.order.api.request.plugin.PluginPayInfoModifyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * 插件支付信息服务
 * @Author: wur
 * @Date: 2021/6/21 10:34
 */
@FeignClient(value = "${application.order.name}", contextId = "PluginPayInfoProvider")
public interface PluginPayInfoProvider {

    /**
     * 新增插件支付信息
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/plugin/payInfo/add")
    BaseResponse add(@RequestBody @Valid PluginPayInfoAddRequest request);

    /**
     * 修改插件支付信息
     * @param request
     * @return
     */
    @PostMapping("/order/${application.order.version}/plugin/payInfo/modify")
    BaseResponse modify(@RequestBody @Valid PluginPayInfoModifyRequest request);

}
