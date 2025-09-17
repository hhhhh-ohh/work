package com.wanmi.sbc.goods.mq;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyEvaluateNumRequest;
import com.wanmi.sbc.goods.goodsevaluate.model.root.GoodsEvaluate;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @ClassName GoodsEvaluateNumMqService
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2019/4/12 14:53
 **/
@Service
public class GoodsEvaluateNumMqService {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * @Author lvzhenwei
     * @Description 统计商品评价数量mq
     * @Date 14:58 2019/4/12
     * @Param [goodsEvaluate]
     * @return void
     **/
    public void updateGoodsEvaluateNum(GoodsEvaluate goodsEvaluate){
        GoodsModifyEvaluateNumRequest request = new GoodsModifyEvaluateNumRequest();
        StoreType storeType = goodsEvaluate.getStoreType();
        if(Objects.nonNull(storeType) && storeType == StoreType.O2O){
            request.setPluginType(PluginType.O2O);
        }
        request.setGoodsId(goodsEvaluate.getGoodsId());
        request.setEvaluateScore(goodsEvaluate.getEvaluateScore());
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_EVALUATE_NUM);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }
}
