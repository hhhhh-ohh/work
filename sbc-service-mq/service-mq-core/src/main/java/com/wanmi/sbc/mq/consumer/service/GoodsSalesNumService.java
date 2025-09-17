package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.bean.RedisHIncrBean;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifySalesNumRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className GoodsSalesNumService
 * @description 统计商品销量MQ
 * @date 2021/8/16 3:55 下午
 */
@Slf4j
@Service
public class GoodsSalesNumService {

    @Autowired private RedisUtil redisService;

    @Bean
    public Consumer<Message<String>> mqGoodsSalesNumService() {
        return message->{
            String json = message.getPayload();
            GoodsModifySalesNumRequest request =
                    JSONObject.parseObject(json, GoodsModifySalesNumRequest.class);
            //更新redis的标识用于更新定时同步
            List<RedisHIncrBean> beans = Lists.newArrayList();
            beans.add(new RedisHIncrBean(request.getGoodsId(), request.getGoodsSalesNum()));
            redisService.hincrPipeline(CacheKeyConstant.GOODS_SALES_ADD, beans);
        };
    }
}
