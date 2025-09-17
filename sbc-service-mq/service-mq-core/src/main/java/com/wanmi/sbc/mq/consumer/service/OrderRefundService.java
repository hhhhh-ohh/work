package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.elastic.api.provider.distributionrecord.EsDistributionRecordProvider;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordByTradeIdRequest;
import com.wanmi.sbc.marketing.api.provider.distributionrecord.DistributionRecordSaveProvider;
import com.wanmi.sbc.marketing.api.request.distributionrecord.DistributionRecordByTradeIdRequest;
import com.wanmi.sbc.mq.order.OrderConsumerService;
import com.wanmi.sbc.order.api.provider.distribution.DistributionTaskTempProvider;
import com.wanmi.sbc.order.api.request.distribution.DistributionTaskTempRequest;
import com.wanmi.sbc.order.bean.vo.DistributionTaskTempVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className OrderRefundService
 * @description 订单退款作废时，发送订单MQ消息
 * @date 2021/8/16 8:48 下午
 */
@Slf4j
@Service
public class OrderRefundService {

    @Autowired private DistributionTaskTempProvider distributionTaskTempProvider;

    @Autowired private OrderConsumerService orderConsumerService;

    @Autowired private DistributionRecordSaveProvider distributionRecordSaveProvider;

    @Autowired private EsDistributionRecordProvider esDistributionRecordProvider;

    @Bean
    public Consumer<Message<String>> mqOrderRefundService() {
        return this::extracted;
    }

    @GlobalTransactional
    private void extracted(Message<String> message) {
        String json = message.getPayload();
        try {
            log.info("=============== 订单退款作废MQ处理start ===============");
            TradeVO tradeVO = JSONObject.parseObject(json, TradeVO.class);

            // 待删除的临时表id集合
            List<String> idList = new ArrayList<>();
            // 根据订单号查询临时表信息
            DistributionTaskTempRequest distributionTaskTempRequest =
                    new DistributionTaskTempRequest();
            distributionTaskTempRequest.setOrderId(tradeVO.getId());
            List<DistributionTaskTempVO> taskTempList =
                    distributionTaskTempProvider
                            .findByOrderId(distributionTaskTempRequest)
                            .getContext()
                            .getDistributionTaskTempVOList();
            if (CollectionUtils.isNotEmpty(taskTempList)) {
                for (DistributionTaskTempVO distributionTaskTemp : taskTempList) {
                    if (Objects.nonNull(distributionTaskTemp.getOrderDisableTime())) {
                        // 有订单失效时间，定时任务会处理，这里不操作相关数据
                        continue;
                    }
                    idList.add(distributionTaskTemp.getId());
                }

                if (CollectionUtils.isNotEmpty(idList)) {
                    // 删除临时表数据
                    DistributionTaskTempRequest taskTempRequest = new DistributionTaskTempRequest();
                    taskTempRequest.setIds(idList);
                    int count =
                            distributionTaskTempProvider
                                    .deleteByIds(taskTempRequest)
                                    .getContext()
                                    .getNum();
                    // 判断临时表数据是否删除成功
                    if (count > 0) {
                        // 减少分销员奖励信息
                        orderConsumerService.dealDistributionCustomerReward(tradeVO, false);

                        // 删除分销记录数据
                        DistributionRecordByTradeIdRequest request =
                                new DistributionRecordByTradeIdRequest(tradeVO.getId());
                        distributionRecordSaveProvider.deleteByTradeId(request);

                        // 删除分销记录数据,es
                        EsDistributionRecordByTradeIdRequest tradeIdRequest =
                                new EsDistributionRecordByTradeIdRequest(tradeVO.getId());
                        esDistributionRecordProvider.deleteByTradeId(tradeIdRequest);
                    }
                }
            }

            log.info("=============== 订单退款作废MQ处理end ===============");
        } catch (Exception e) {
            log.error("订单退款作废MQ处理异常! param={}", json, e);
            throw e;
        }
    }
}
