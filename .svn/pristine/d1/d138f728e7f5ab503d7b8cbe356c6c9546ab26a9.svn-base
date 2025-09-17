package com.wanmi.sbc.empower.provider.impl.channel.logistics;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.empower.api.provider.channel.logistics.ChannelLogisticsQueryProvider;
import com.wanmi.sbc.empower.api.request.channel.logistics.ChannelLogisticsQueryRequest;
import com.wanmi.sbc.empower.api.response.channel.logistics.ChannelLogisticsQueryResponse;
import com.wanmi.sbc.empower.channel.base.ChannelServiceFactory;
import com.wanmi.sbc.empower.channel.logistics.ChannelGetLogisticsService;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * @className LogisticsController
 * @description 抽象化查询物流Controller
 * @author 张文昌
 * @date 2021/5/29 17:28
 */
@RestController
public class ChannelLogisticsQueryController implements ChannelLogisticsQueryProvider {

    @Resource private ChannelServiceFactory channelServiceFactory;

    /**
     * 查询物流信息
     * @param request
     * @return
     */
    @Override
    public BaseResponse<ChannelLogisticsQueryResponse> getLogisticsList(ChannelLogisticsQueryRequest request) {
        // 三方类型
        ThirdPlatformType type = request.getThirdPlatformType();
        ChannelLogisticsQueryResponse response =
                channelServiceFactory
                        .getChannelService(ChannelGetLogisticsService.class, type)
                        .getLogisticsList(request);
        return BaseResponse.success(response);
    }
}
