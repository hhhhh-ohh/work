package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerSaveProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerUpgradeRequest;
import com.wanmi.sbc.customer.api.response.distribution.EsDistributionCustomerByCustomerIdResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerShowPhoneVO;
import com.wanmi.sbc.elastic.api.provider.customer.EsDistributionCustomerProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerAddRequest;
import com.wanmi.sbc.marketing.bean.enums.DistributionLimitType;
import com.wanmi.sbc.mq.distribution.DistributionCacheService;
import com.wanmi.sbc.mq.order.OrderConsumerService;
import com.wanmi.sbc.order.bean.vo.TradeVO;

import io.seata.spring.annotation.GlobalTransactional;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className OrderPayedService
 * @description 订单支付后，发送订单支付MQ消息
 * @date 2021/8/16 4:01 下午
 */
@Slf4j
@Service
public class OrderPayedService {

    @Autowired private RedissonClient redissonClient;

    @Autowired private DistributionCustomerSaveProvider distributionCustomerSaveProvider;

    @Autowired private EsDistributionCustomerProvider esDistributionCustomerProvider;

    @Autowired private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Autowired private DistributionCacheService distributionCacheService;

    @Autowired private OrderConsumerService orderConsumerService;

    @Bean
    public Consumer<Message<String>> mqOrderPayedService() {
        return this::extracted;
    }

    @SneakyThrows
    @GlobalTransactional
    private void extracted(Message<String> message) {
        String json = message.getPayload();
        TradeVO tradeVO = JSONObject.parseObject(json, TradeVO.class);
        log.info("=============== 订单支付MQ-2处理start:{}", json);
        //排除卡券订单
        if(!Objects.isNull(tradeVO.getOrderTag()) && Boolean.TRUE.equals(tradeVO.getOrderTag().getElectronicCouponFlag())) {
            log.warn("卡券订单不处理。订单ID：{}", tradeVO.getId());
            return;
        }
        RLock rLock = redissonClient.getFairLock("doOrderPayed" + tradeVO.getId());
        try {
            if (rLock.tryLock(10, 10, TimeUnit.SECONDS)) {
                log.info("=============== 订单支付MQ处理start ===============");
                // 新增分销任务临时记录
                orderConsumerService.addTaskTemp(tradeVO);
                // 新增分销记录表记录
                orderConsumerService.addDistributionRecord(tradeVO);
                // 增加分销员奖励信息
                orderConsumerService.dealDistributionCustomerReward(tradeVO, true);
                // 如果购买的是开店礼包，升级为小B
                if (DefaultFlag.YES.equals(tradeVO.getStoreBagsFlag())) {
                    DistributionCustomerUpgradeRequest request =
                            new DistributionCustomerUpgradeRequest();
                    request.setCustomerId(tradeVO.getBuyer().getId());
                    DistributionLimitType distributionLimitType =
                            distributionCacheService.getBaseLimitType();
                    request.setBaseLimitType(
                            Objects.nonNull(distributionLimitType)
                                    ? DefaultFlag.fromValue(distributionLimitType.toValue())
                                    : null);
                    request.setDistributorLevelVOList(
                            distributionCacheService.getDistributorLevels());
                    distributionCustomerSaveProvider.upgrade(request);
                    // 同步es
                    if (StringUtils.isNotBlank(request.getCustomerId())) {
                        DistributionCustomerByCustomerIdRequest idRequest =
                                new DistributionCustomerByCustomerIdRequest();
                        idRequest.setCustomerId(request.getCustomerId());
                        EsDistributionCustomerByCustomerIdResponse idResponse =
                                distributionCustomerQueryProvider
                                        .getByCustomerIdShowPhone(idRequest)
                                        .getContext();
                        DistributionCustomerShowPhoneVO distributionCustomerVO =
                                idResponse.getDistributionCustomerVO();
                        if (Objects.nonNull(distributionCustomerVO)) {
                            EsDistributionCustomerAddRequest addRequest =
                                    new EsDistributionCustomerAddRequest(
                                            Collections.singletonList(distributionCustomerVO));
                            esDistributionCustomerProvider.add(addRequest);
                        }
                    }
                }
                log.info("=============== 订单支付MQ处理end ===============");
            } else {
                log.info("订单编号：{}，重复分销任务doOrderPayed", tradeVO.getId());
            }
        } catch (Exception e) {
            log.error("订单支付MQ处理异常! param={}", json, e);
            throw e;
        } finally {
            rLock.unlock();
        }
    }
}
