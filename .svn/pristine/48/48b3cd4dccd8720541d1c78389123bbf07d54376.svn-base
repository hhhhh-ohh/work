package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.goods.api.provider.goodscatethirdcaterel.GoodsCateThirdCateRelProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsByThirdCateRequest;
import com.wanmi.sbc.goods.api.response.goodscatethirdcaterel.GoodsCateThirdCateRelAddResponse;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author houshuai
 * @date 2022/4/2 11:35
 * @description <p> </p>
 */
@Slf4j
@Service
public class ThirdCateSyncService {

    @Autowired
    private GoodsCateThirdCateRelProvider goodsCateThirdCateRelProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EsStandardProvider esStandardProvider;

    @Bean
    public Consumer<Message<String>> mqThirdCateSyncService() {
        return this::extracted;
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    public void extracted(Message<String> message) {
        String json = message.getPayload();
        log.info("同步处理商品和商品库，{}",json);
        GoodsByThirdCateRequest request = JSONObject.parseObject(json, GoodsByThirdCateRequest.class);
        GoodsCateThirdCateRelAddResponse response = goodsCateThirdCateRelProvider.modifyThirdCate(request).getContext();
        List<String> goodsIdList = response.getUpdateEsGoodsIds();
        if(CollectionUtils.isNotEmpty(goodsIdList)){
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsIds(goodsIdList).build());
        }
        List<String> standardGoodsIdList = response.getUpdateEsStandardGoodsIds();
        if(CollectionUtils.isNotEmpty(standardGoodsIdList)){
            esStandardProvider.init(EsStandardInitRequest.builder().goodsIds(standardGoodsIdList).build());
        }
    }
}
