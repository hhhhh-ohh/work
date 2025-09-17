package com.wanmi.sbc.customer.storeevaluate.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.customer.storeevaluatesum.model.root.StoreEvaluateSum;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoodsEvaluateMqService {

    @Autowired
    private MqSendProvider mqSendProvider;

    public void sendStoreEvaluate(List<StoreEvaluateSum> storeEvaluateSumList) {

        try {
            MqSendDTO mqSendDTO =  new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.ADD_STORE_EVALUATE);
            mqSendDTO.setData(JSONObject.toJSONString(storeEvaluateSumList));
            mqSendProvider.send(mqSendDTO);
        } catch (Exception e) {
            log.info("发送商家评价异常：{}", e.getMessage());
        }

    }
}