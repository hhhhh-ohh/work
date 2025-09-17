package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInitProviderGoodsInfoRequest;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsProviderStatusRequest;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author lvzhenwei
 * @className ModifyGoodsProviderStatusService
 * @description 更新商品商家状态
 * @date 2021/8/17 5:42 下午
 */
@Slf4j
@Service
public class ModifyGoodsProviderStatusService {

    @Autowired private GoodsProvider goodsProvider;

    @Autowired private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Bean
    public Consumer<Message<String>> mqModifyGoodsProviderStatusService() {
        return this::extracted;
    }

    @GlobalTransactional
    private void extracted(Message<String> message) {
        String json = message.getPayload();
        log.info("商品消费服务=GoodsConsumerService.updateGoodsProviderStatus===>{}", json);
        GoodsProviderStatusRequest request =
                JSONObject.parseObject(json, GoodsProviderStatusRequest.class);
        goodsProvider.updateProviderStatus(request);
        // 更新关联的商家商品
        request.getStoreIds()
                .forEach(
                        id ->
                                esGoodsInfoElasticProvider.initProviderEsGoodsInfo(
                                        EsGoodsInitProviderGoodsInfoRequest.builder()
                                                .storeId(id)
                                                .providerGoodsIds(null)
                                                .build()));
    }


}
