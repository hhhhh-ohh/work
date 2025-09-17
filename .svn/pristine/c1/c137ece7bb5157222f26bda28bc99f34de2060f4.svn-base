package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.goods.api.provider.pointsgoods.PointsGoodsSaveProvider;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsSalesModifyRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className PointsGoodsSalesNumService
 * @description 统计积分商品销量MQ
 * @date 2021/8/16 3:11 下午
 */
@Slf4j
@Service
public class PointsGoodsSalesNumService {

    @Autowired private PointsGoodsSaveProvider pointsGoodsSaveProvider;

    @Bean
    public Consumer<Message<String>> mqPointsGoodsSalesNumService() {
        return message->{
            String json = message.getPayload();
            PointsGoodsSalesModifyRequest request =
                    JSONObject.parseObject(json, PointsGoodsSalesModifyRequest.class);
            pointsGoodsSaveProvider.updatePointsGoodsSalesNum(request);
        };
    }
}
