package com.wanmi.sbc.marketing.provider.impl.mqconsumer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.mqconsumer.MarketingMqConsumerProvider;
import com.wanmi.sbc.marketing.api.request.mqconsumer.MarketingMqConsumerRequest;
import com.wanmi.sbc.marketing.mqconsumer.MarketingMqConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author lvzhenwei
 * @className MarketingMqConsumerController
 * @description mq消费接口实现
 * @date 2021/8/13 5:12 下午
 **/
@RestController
public class MarketingMqConsumerController implements MarketingMqConsumerProvider {

    @Autowired
    private MarketingMqConsumerService marketingMqConsumerService;

    @Override
    public BaseResponse marketCouponInviteNewAdd(@RequestBody @Valid MarketingMqConsumerRequest request) {
        marketingMqConsumerService.marketCouponInviteNewAdd(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse levelRightsIssueCoupons(@Valid MarketingMqConsumerRequest request) {
        marketingMqConsumerService.levelRightsIssueCoupons(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse grouponStatisticsNum(@Valid MarketingMqConsumerRequest request) {
        marketingMqConsumerService.grouponStatisticsNum(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }
}
