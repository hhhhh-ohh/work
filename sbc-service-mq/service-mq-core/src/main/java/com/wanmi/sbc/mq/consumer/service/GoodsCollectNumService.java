package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyCollectNumRequest;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className GoodsCollectNumService
 * @description TODO
 * @date 2021/8/16 3:20 下午
 */
@Slf4j
@Service
public class GoodsCollectNumService {

    @Autowired private GoodsProvider goodsProvider;

    @Autowired private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired private RedisUtil redisService;

    @Bean
    public Consumer<Message<String>> mqGoodsCollectNumService() {
        return this::extracted;
    }

    @GlobalTransactional
    private void extracted(Message<String> message) {
        String json = message.getPayload();
        GoodsModifyCollectNumRequest request =
                JSONObject.parseObject(json, GoodsModifyCollectNumRequest.class);
        // 更新redis商品基本数据
        String goodsDetailInfo =
                redisService.getString(RedisKeyConstant.GOODS_DETAIL_CACHE + request.getGoodsId());
        if (StringUtils.isNotBlank(goodsDetailInfo)) {
            redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + request.getGoodsId());
        }
        goodsProvider.updateGoodsCollectNum(request);
        esGoodsInfoElasticProvider.initEsGoodsInfo(
                EsGoodsInfoRequest.builder().goodsId(request.getGoodsId()).build());
    }


}
