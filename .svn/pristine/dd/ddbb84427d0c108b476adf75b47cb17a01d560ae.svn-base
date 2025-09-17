package com.wanmi.sbc.marketing.api.provider.mqconsumer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.mqconsumer.MarketingMqConsumerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description mq消费接口
 * @author  lvzhenwei
 * @date 2021/8/13 5:09 下午
 **/
@FeignClient(value = "${application.marketing.name}", contextId = "MarketingMqConsumerProvider")
public interface MarketingMqConsumerProvider {

    /**
     * @description 邀新注册-发放优惠券
     * @author  lvzhenwei
     * @date 2021/8/16 11:35 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/marketing/${application.marketing.version}/mq/consumer/market-coupon-invite-new-add")
    BaseResponse marketCouponInviteNewAdd(@RequestBody @Valid MarketingMqConsumerRequest request);

    /**
     * @description 发放优惠券
     * @author  lvzhenwei
     * @date 2021/8/16 11:36 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/marketing/${application.marketing.version}/mq/consumer/level-rights-issue-coupons")
    BaseResponse levelRightsIssueCoupons(@RequestBody @Valid MarketingMqConsumerRequest request);

    /**
     * @description 根据不同拼团状态更新不同的统计数据（已成团、待成团、团失败人数）
     * @author  lvzhenwei
     * @date 2021/8/17 1:53 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/marketing/${application.marketing.version}/mq/consumer/groupon-statistics-num")
    BaseResponse grouponStatisticsNum(@RequestBody @Valid MarketingMqConsumerRequest request);




}
