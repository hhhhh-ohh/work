package com.wanmi.sbc.empower.api.provider.channel.logistics;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.logistics.ChannelLogisticsQueryRequest;
import com.wanmi.sbc.empower.api.response.channel.logistics.ChannelLogisticsQueryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @className ChannelLogisticsQueryProvider
 * @description 获取第三方物流provider
 * @author    张文昌
 * @date      2021/5/29 20:09
 */
@FeignClient(value = "${application.empower.name}", contextId = "ChannelLogisticsQueryProvider")
public interface ChannelLogisticsQueryProvider {

    /***
     * 查询物流信息
     * @param logisticsQueryRequest 查询请求
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/sync/goods/get-logistics")
    BaseResponse<ChannelLogisticsQueryResponse> getLogisticsList(
            @RequestBody @Valid ChannelLogisticsQueryRequest logisticsQueryRequest);
}
