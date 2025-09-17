package com.wanmi.sbc.order.common;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyCollectNumRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName GoodsCollectNumMq
 * @Description 商品收藏量mq
 * @Author lvzhenwei
 * @Date 2019/4/12 10:00
 **/
@Service
public class GoodsCollectNumMq {

    @Autowired
    private MqSendProvider mqSendProvider;

    public void updateGoodsCollectNum(GoodsModifyCollectNumRequest goodsModifyCollectNumRequest){
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_COLLECT_NUM);
        mqSendDTO.setData(JSONObject.toJSONString(goodsModifyCollectNumRequest));
        mqSendProvider.send(mqSendDTO);
    }
}
