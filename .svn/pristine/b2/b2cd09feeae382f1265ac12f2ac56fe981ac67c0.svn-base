package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.OperatorInteger;
import com.wanmi.sbc.common.util.IterableUtils;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeBatchSendRequest;
import com.wanmi.sbc.marketing.bean.dto.CouponActivityConfigAndCouponInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @description 精准发券
 */
@Slf4j
@Service
public class MarketSendCouponCodeService {

    @Autowired private CouponCodeProvider couponCodeProvider;

    @Bean
    public Consumer<Message<String>> mqMarketSendCouponCodeService() {
        return message->{
            String json = message.getPayload();
            CouponCodeBatchSendRequest sendCouponRequest = JSONObject.parseObject(json, CouponCodeBatchSendRequest.class);
            //couponCodeProvider.sendBatchCouponCodeAsyncByCustomers(sendCouponRequest);
            List<String> customerIds = sendCouponRequest.getCustomerIds();
            OperatorInteger operatorInteger = OperatorInteger.valueOf(OperatorInteger.SPLIT.name());
            int maxSize = operatorInteger.apply(customerIds.size());
            List<List<String>> splitList = IterableUtils.splitList(customerIds, maxSize);
            sendCouponAsync(splitList, sendCouponRequest.getList());
        };
    }

    public void sendCouponAsync(List<List<String>> splitList, List<CouponActivityConfigAndCouponInfoDTO> configAndCouponInfoS) {
        try {
            final CountDownLatch count = new CountDownLatch(splitList.size());
            ExecutorService executor = Executors.newFixedThreadPool(splitList.size());
            for (List<String> customerIds : splitList) {
                    executor.execute(() -> {
                            try {
                                    CouponCodeBatchSendRequest sendCouponRequest = new CouponCodeBatchSendRequest();
                                    sendCouponRequest.setCustomerIds(customerIds);
                                    sendCouponRequest.setList(configAndCouponInfoS);
                                    couponCodeProvider.sendBatchCouponCodeAsyncByCustomers(sendCouponRequest);
                                    log.info("发券子批次处理成功,活动编号:{},子批次处理总数量{}==========>",
                                                    configAndCouponInfoS.get(0).getActivityId(),
                                                    customerIds.size());
                                } catch (Exception e) {
                                    log.error("发券子批次处理失败,活动编号:{},子批次处理总数量{}==========>",
                                                    configAndCouponInfoS.get(0).getActivityId(),
                                                    customerIds.size());
                                } finally {
                                    // 无论是否报错始终执行countDown()，否则报错时主进程一直会等待线程结束
                                    count.countDown();
                                }
                        });
                }
            count.await();
            executor.shutdown();
        } catch (Exception e) {
            log.error("异步编排导入异常：{}", e.getMessage());
        }
    }

}
