package com.wanmi.sbc.empower.api.provider.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelAddressRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelAddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author wur
 * @className ChannelAddressProvider
 * @description 渠道地址服务
 * @date 2021/5/11 10:28
 */
@FeignClient(value = "${application.empower.name}", contextId = "ChannelAddressProvider")
public interface ChannelAddressProvider {

    /**
    * 查询地址信息
     * @author  wur
     * @date: 2021/5/13 15:00
     * @param response 查询地址请求
     * @return    返回地址数据
     **/
    @PostMapping("/channel/${application.empower.version}/address/get-province")
    BaseResponse<ChannelAddressResponse> getAddress(@RequestBody @Valid ChannelAddressRequest response);

}
