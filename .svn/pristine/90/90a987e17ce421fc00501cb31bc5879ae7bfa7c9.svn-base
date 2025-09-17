package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.mqconsumer.GoodsMqConsumerProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsInfoVendibilityRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsListByIdsRequest;
import com.wanmi.sbc.goods.api.request.mqconsumer.GoodsMqConsumerRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author wur
 * @className GoodsInfoVendibilityService
 * @description 商品-批量处理商品的可售性
 * @date 2023/8/11 14:22
 **/
@Slf4j
@Service
public class GoodsInfoVendibilityService {

    @Autowired
    private GoodsMqConsumerProvider goodsMqConsumerProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Bean
    public Consumer<Message<String>> mqGoodsInfoVendibilityService() {
        return message->{
            String json = message.getPayload();
            GoodsMqConsumerRequest goodsMqConsumerRequest = new GoodsMqConsumerRequest();
            goodsMqConsumerRequest.setMqContentJson(json);
            goodsMqConsumerProvider.buildGoodsInfoVendibility(goodsMqConsumerRequest);

            // 刷新ES
            GoodsInfoVendibilityRequest request = JSONObject.parseObject(json, GoodsInfoVendibilityRequest.class);
            if (CollectionUtils.isNotEmpty(request.getGoodsIdList())) {
                List<GoodsVO> goodsVOList = goodsQueryProvider.listByProviderGoodsId(GoodsListByIdsRequest.builder().goodsIds(request.getGoodsIdList()).build()).getContext().getGoodsVOList();
                log.info("-----------  goodsinfoVendibility  goodsVOList={}", JSONObject.toJSONString(goodsVOList));
                if (CollectionUtils.isNotEmpty(goodsVOList)) {
                    log.info("-----------  goodsinfoVendibility  goodsIdList={}", JSONObject.toJSONString(goodsVOList.stream().map(GoodsVO::getGoodsId).collect(Collectors.toList())));
                    esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().idList(goodsVOList.stream().map(GoodsVO::getGoodsId).collect(Collectors.toList())).build());
                }
            }
        };
    }
}