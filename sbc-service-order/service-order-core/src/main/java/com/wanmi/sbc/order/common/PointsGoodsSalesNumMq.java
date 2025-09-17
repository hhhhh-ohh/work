package com.wanmi.sbc.order.common;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsSalesModifyRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName PointsGoodsSalesNumMq
 * @Description 积分商品增加销量
 * @Author lvzhenwei
 * @Date 2019/5/29 10:27
 **/
@Service
public class PointsGoodsSalesNumMq {

    @Autowired
    private MqSendProvider mqSendProvider;

    public void updatePointsGoodsSalesNumMq(PointsGoodsSalesModifyRequest request) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.POINTS_GOODS_SALES_NUM);
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendProvider.send(mqSendDTO);
    }
}
