package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.mq.distribution.DistributionTaskTempService;
import com.wanmi.sbc.order.api.provider.trade.ProviderTradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.request.distribution.ReturnOrderSendMQRequest;
import com.wanmi.sbc.order.api.request.trade.ProviderTradeModifyReturnOrderNumByRidRequest;
import com.wanmi.sbc.order.api.request.trade.TradeReturnOrderNumUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className OrderServiceReturnOrderFlowService
 * @description 退单状态变更，发送MQ消息
 * @date 2021/8/16 3:00 下午
 */
@Slf4j
@Service
public class OrderServiceReturnOrderFlowService {

    /** 注入分销临时任务service */
    @Autowired private DistributionTaskTempService distributionTaskTempService;

    @Autowired private TradeProvider tradeProvider;

    @Autowired private ProviderTradeProvider providerTradeProvider;

    @Bean
    public Consumer<Message<String>> mqOrderServiceReturnOrderFlowService() {
        return this::extracted;
    }

    public void extracted(Message<String> message) {
        String json = message.getPayload();
        try {
            log.info("=============== 退单状态变更MQ处理start ===============");
            ReturnOrderSendMQRequest request =
                    JSONObject.parseObject(json, ReturnOrderSendMQRequest.class);

            if (request.isAddFlag()) {
                // 分销任务临时表退单数量加1
                distributionTaskTempService.addReturnOrderNum(request.getOrderId());
            } else {
                // 分销任务临时表退单数量减1
                distributionTaskTempService.minusReturnOrderNum(request.getOrderId());
            }

            // 更新订单正在进行的退单数量
            tradeProvider.updateReturnOrderNum(
                    TradeReturnOrderNumUpdateRequest.builder()
                            .tid(request.getOrderId())
                            .addFlag(request.isAddFlag())
                            .build());
            // 更新供应商正在进行的退单数量
            if (StringUtils.isNotBlank(request.getReturnId())) {
                providerTradeProvider.updateReturnOrderNumByRid(
                        ProviderTradeModifyReturnOrderNumByRidRequest.builder()
                                .returnOrderId(request.getReturnId())
                                .addFlag(request.isAddFlag())
                                .build());
            }
            log.info("=============== 退单状态变更MQ处理end ===============");
        } catch (Exception e) {
            log.error("退单状态变更MQ处理异常! param={}", json, e);
        }
    }
}
