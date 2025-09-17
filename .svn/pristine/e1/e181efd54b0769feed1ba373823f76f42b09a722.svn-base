package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.marketing.api.provider.goods.GoodsEditSynProvider;
import com.wanmi.sbc.marketing.api.request.goods.GoodsEditSynRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.function.Consumer;

/**
 * @description 精准发券
 */
@Slf4j
@Service
public class GoodsEditService {

    @Autowired private GoodsEditSynProvider goodsEditSynProvider;

    @Bean
    public Consumer<Message<String>> mqGoodsEditService() {
        return message->{
            String json = message.getPayload();
            log.info("============== 商品变更 ======= begin：{}", json);
            GoodsEditSynRequest goodsEditSynRequest = JSONObject.parseObject(json, GoodsEditSynRequest.class);
            if (CollectionUtils.isEmpty(goodsEditSynRequest.getGoodsIds())
                    && CollectionUtils.isEmpty(goodsEditSynRequest.getGoodsInfoIds())) {
                return;
            }
            //营销活动处理
            goodsEditSynProvider.goodsEdit(goodsEditSynRequest);
        };
    }
}
