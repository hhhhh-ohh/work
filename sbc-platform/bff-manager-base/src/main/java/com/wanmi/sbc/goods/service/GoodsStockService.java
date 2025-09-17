package com.wanmi.sbc.goods.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoModifyAddedStatusRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInitProviderGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsInfoVendibilityRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyAddedStatusRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoModifyAddedStatusRequest;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.mq.producer.ManagerBaseProducerService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品缓存服务
 * Created by daiyitian on 2017/4/11.
 */
@Slf4j
@Service
public class GoodsStockService {


    @Autowired
    private RedisUtil redisService;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private ManagerBaseProducerService managerBaseProducerService;

    @Autowired
    private GoodsProvider goodsProvider;

    @Autowired
    private EsStandardProvider esStandardProvider;

    @Autowired
    private MqSendProvider mqSendProvider;

    // 上架/下架
    @GlobalTransactional
    public void addedOrtakedown(List<GoodsInfoVO> goodsInfoList, AddedFlag addedFlag){
        // 所以GoodsIdList，正常Ids，O2oIds
        List<String> goodsIds = goodsInfoList.stream().map(GoodsInfoVO::getGoodsInfoId)
                .distinct().collect(Collectors.toList());
        List<String> normalIds = goodsInfoList.stream().filter(g->!PluginType.O2O.equals(g.getPluginType()))
                .map(GoodsInfoVO::getGoodsInfoId).distinct().collect(Collectors.toList());
        List<String> o2oIds = goodsInfoList.stream().filter(g->PluginType.O2O.equals(g.getPluginType()))
                .map(GoodsInfoVO::getGoodsInfoId).distinct().collect(Collectors.toList());
        // Mysql上架
        goodsInfoProvider.modifyAddedStatus(GoodsInfoModifyAddedStatusRequest.builder().addedFlag(addedFlag.toValue())
                .goodsInfoIds(goodsIds)
                .build());

        // ES 库不同，因此ES分开更新
        if (WmCollectionUtils.isNotEmpty(normalIds)) {
            esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder()
                            .addedFlag(addedFlag.toValue())
                            .goodsIds(null)
                            .goodsInfoIds(normalIds)
                            .pluginType(PluginType.NORMAL)
                            .build());
        }
        if (WmCollectionUtils.isNotEmpty(o2oIds)) {
            esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder()
                            .addedFlag(addedFlag.toValue())
                            .goodsIds(null)
                            .goodsInfoIds(o2oIds)
                            .pluginType(PluginType.O2O)
                            .build());
        }

        // 更新积分商品ES
        managerBaseProducerService.sendMQForModifyPointsGoodsAddedFlag(goodsIds, addedFlag.toValue());
    }

    //供应商上架
    @GlobalTransactional
    public void providerGoodsAdded(List<GoodsInfoVO> goodsInfoList, AddedFlag addedFlag){
        List<String> goodsIds = goodsInfoList.stream().map(GoodsInfoVO::getGoodsId).distinct().collect(Collectors.toList());
        List<String> goodsInfoIds = goodsInfoList.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        GoodsModifyAddedStatusRequest request = new GoodsModifyAddedStatusRequest();
        request.setAddedFlag(addedFlag.toValue());
        request.setGoodsIds(goodsIds);
        request.setGoodsInfoIds(goodsInfoIds);
        request.setJobIn(BoolFlag.YES);
        //供应商商品上架
        goodsProvider.providerModifyAddedStatusByTiming(request);

        esStandardProvider.init(EsStandardInitRequest.builder().relGoodsIds(request.getGoodsIds()).build());
        //更新ES
        esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder().
                addedFlag(addedFlag.toValue()).goodsIds(request.getGoodsIds()).goodsInfoIds(null).build());
        //更新关联的商家商品es
        esGoodsInfoElasticProvider.initProviderEsGoodsInfo(EsGoodsInitProviderGoodsInfoRequest.builder().
                storeId(null).providerGoodsIds(request.getGoodsIds()).build());

        // 发mq异步处理
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.BATCH_GOODSINFO_VENDIBILITY);
        GoodsInfoVendibilityRequest vendibilityRequest = new GoodsInfoVendibilityRequest();
        vendibilityRequest.setGoodsIdList(goodsIds);
        vendibilityRequest.setGoodsInfoIdList(goodsInfoIds);
        mqSendDTO.setData(JSONObject.toJSONString(vendibilityRequest));
        mqSendProvider.send(mqSendDTO);
    }
}
