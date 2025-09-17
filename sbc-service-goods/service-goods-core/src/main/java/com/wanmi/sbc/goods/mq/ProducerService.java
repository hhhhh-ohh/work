package com.wanmi.sbc.goods.mq;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.StoreMessageMQRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.goods.api.request.common.GoodsCommonBatchAddRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsSaveDTO;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MQ生产者
 * @author: dyt
 * @createDate: 2019/2/25 13:57
 * @version: 1.0
 */
@Service
@Slf4j
public class ProducerService {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 根据商品库id更新商品库
     * @param standardIds 商品库id
     */
    public void initStandardByStandardIds(List<String> standardIds){
        Map<String,List<String>> map = new HashMap<>();
        map.put("goodsIds", standardIds);
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_STANDARD_INIT);
        mqSendDTO.setData(JSONObject.toJSONString(map));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 根据商品SkuId更新商品库
     * @param skuIds 商品SkuId
     */
    public void initGoodsBySkuIds(List<String> skuIds){
        initGoodsBySkuIds(skuIds, PluginType.NORMAL);
    }

    /**
     * 根据商品SkuId更新商品库
     * @param skuIds 商品SkuId
     */
    public void initGoodsBySkuIds(List<String> skuIds, PluginType pluginType){
        EsGoodsInfoRequest request = EsGoodsInfoRequest.builder().skuIds(skuIds).pluginType(pluginType).build();
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_GOODS_INIT);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * ES积分商品增加销量
     * @param pointsGoodsId 积分商品id
     * @param sales 销量
     */
    public void addSales(String pointsGoodsId, Long sales){
        Map<String, Object> map = new HashMap<>();
        map.put("pointsGoodsId", pointsGoodsId);
        map.put("sales", sales);
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_POINTS_GOODS_ADD_SALES);
        mqSendDTO.setData(JSONObject.toJSONString(map));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 发送商家消息
     * @param request 请求
     */
    public void sendStoreMessage(StoreMessageMQRequest request){
        log.info("商家消息发送开始，request:{}", request);
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.STORE_MESSAGE_SEND);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }

}
