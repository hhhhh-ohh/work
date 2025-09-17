package com.wanmi.sbc.mq;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsStoreInfoModifyRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyStoreNameByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsProviderStatusRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsProducer {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 更新代销商品的供应商店铺状态
     * @param storeIds
     */
    public void updateProviderStatus(Integer providerStatus, List<Long> storeIds){
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.MODIFY_GOODS_PROVIDER_STATUS);
        mqSendDTO.setData(JSONObject.toJSONString(new GoodsProviderStatusRequest(providerStatus, storeIds)));
        mqSendProvider.send(mqSendDTO);
    }


    /**
     * ES商品更新店铺信息
     * @param request
     */
    public void updateStoreStateByStoreId(EsGoodsStoreInfoModifyRequest request){
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_GOODS_MODIFY_STORE_STATE);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }

    /**
     * 商品-更新店铺信息时，更新商品goods中对应店铺名称
     * @param request
     */
    public void updateGoodsStoreNameByStoreId(GoodsModifyStoreNameByStoreIdRequest request){
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.ES_GOODS_MODIFY_STORE_NAME);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }
}
