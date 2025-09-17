package com.wanmi.sbc.mq.appointment;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName RushToAppointmentSaleGoodsMqService
 * @Description 立刻预约同步数量mq
 * @Author zxd
 * @Date 2020/05/25 10:30
 **/
@Slf4j
@Component
public class RushToAppointmentSaleGoodsMqService {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * @return void
     * @Author zxd
     * @Description 立刻预约同步数量mq
     * @Date 10:15 2020/05/25
     * @Param [rushToAppointmentSaleGoodsMq]
     **/
    public void rushToAppointmentSaleGoodsMq(String rushToAppointmentSaleGoodsMessage) {
        log.info("-----发送立即预约MQ-----");
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.APPOINTMENT_SALE_GOODS);
        mqSendDTO.setData(JSONObject.toJSONString(rushToAppointmentSaleGoodsMessage));
        mqSendProvider.send(mqSendDTO);
    }
}
