package com.wanmi.sbc.mq.consumer.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInitProviderGoodsInfoRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelSkuOffAddedSyncRequest;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoModifyAddedStatusByProviderRequest;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author lvzhenwei
 * @className ThirdPlatformSkuOffAddedFlagService
 * @description 第三方平台，下架SKU商品
 * @date 2021/8/12 5:03 下午
 */
@Slf4j
@Service
public class ThirdPlatformSkuOffAddedFlagService {

    @Autowired private GoodsInfoProvider goodsInfoProvider;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired private RedisUtil redisService;

    @Bean
    public Consumer<Message<String>> mqThirdPlatformSkuOffAddedFlagService() {
        return this::extracted;
    }

    @GlobalTransactional
    private void extracted(Message<String> message) {
        String json = message.getPayload();
        log.info("第三方商品下架服务=GoodsConsumerService.doThirdPlatformNoAddedSku===>{}", json);
        ChannelSkuOffAddedSyncRequest request =
                JSONObject.parseObject(json, ChannelSkuOffAddedSyncRequest.class);
        if (CollectionUtils.isEmpty(request.getProviderSkuId())) {
            return;
        }
        // 仅提取供应商商品
        List<GoodsInfoVO> goodsInfoList =
                goodsInfoQueryProvider
                        .listByCondition(
                                GoodsInfoListByConditionRequest.builder()
                                        .goodsInfoIds(request.getProviderSkuId())
                                        .goodsSourceList(
                                                Arrays.asList(
                                                        GoodsSource.LINKED_MALL, GoodsSource.VOP))
                                        .addedFlag(AddedFlag.YES.toValue())
                                        .build())
                        .getContext()
                        .getGoodsInfos();

        if (CollectionUtils.isNotEmpty(goodsInfoList)) {
            List<String> providerSkuIds =
                    goodsInfoList.stream()
                            .map(GoodsInfoVO::getGoodsInfoId)
                            .collect(Collectors.toList());
            // 下架，内含处理标品库/商家商品
            goodsInfoProvider.modifyAddedStatusByProvider(
                    GoodsInfoModifyAddedStatusByProviderRequest.builder()
                            .providerGoodsInfoIds(providerSkuIds)
                            .addedFlag(AddedFlag.NO)
                            .build());

            // 刷新ES
            List<String> providerSpuIds =
                    goodsInfoList.stream()
                            .map(GoodsInfoVO::getGoodsId)
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(providerSpuIds)) {
                // 更新ES
                esGoodsInfoElasticProvider.deleteByGoods(
                        EsGoodsDeleteByIdsRequest.builder().deleteIds(providerSpuIds).build());
                esGoodsInfoElasticProvider.initProviderEsGoodsInfo(
                        EsGoodsInitProviderGoodsInfoRequest.builder()
                                .storeId(null)
                                .providerGoodsIds(providerSpuIds)
                                .build());
            }

            // 删除redis
            List<GoodsInfoVO> supplierGoodsInfoList =
                    goodsInfoQueryProvider
                            .listByCondition(
                                    GoodsInfoListByConditionRequest.builder()
                                            .delFlag(DeleteFlag.NO.toValue())
                                            .providerGoodsInfoIds(request.getProviderSkuId())
                                            .build())
                            .getContext()
                            .getGoodsInfos();
            if (CollectionUtils.isNotEmpty(supplierGoodsInfoList)) {
                List<String> spuIdList =
                        supplierGoodsInfoList.stream()
                                .map(
                                        i ->
                                                RedisKeyConstant.GOODS_DETAIL_CACHE.concat(
                                                        Objects.toString(i.getGoodsId())))
                                .collect(Collectors.toList());
                redisService.del(spuIdList);
            }
        }
    }
}
