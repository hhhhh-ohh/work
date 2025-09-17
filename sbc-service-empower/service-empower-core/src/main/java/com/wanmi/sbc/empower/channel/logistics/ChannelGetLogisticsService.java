package com.wanmi.sbc.empower.channel.logistics;

import com.wanmi.sbc.empower.api.request.channel.logistics.ChannelLogisticsQueryRequest;
import com.wanmi.sbc.empower.api.response.channel.logistics.ChannelLogisticsQueryResponse;
import com.wanmi.sbc.empower.channel.base.ChannelBaseService;

/**
 * @className ChannelGetLogisticsService
 * @description
 * @author 张文昌
 * @date 2021/5/29 17:43
 */
public interface ChannelGetLogisticsService extends ChannelBaseService {
    /**
     * 获取物流信息
     * @param request
     * @return
     */
    ChannelLogisticsQueryResponse getLogisticsList(ChannelLogisticsQueryRequest request);
}
