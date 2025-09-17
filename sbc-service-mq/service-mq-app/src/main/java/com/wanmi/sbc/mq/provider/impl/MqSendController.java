package com.wanmi.sbc.mq.provider.impl;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.api.response.MqSendResponse;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import com.wanmi.sbc.mq.delay.service.DelayJobService;
import com.wanmi.sbc.mq.producer.CommonProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author zhanggaolei
 * @className MqSendController
 * @description mq服务消息发送操作
 * @date 2021/8/5 4:51 下午
 */
@RestController
public class MqSendController implements MqSendProvider {
    @Autowired CommonProducerService commonProducerService;

    @Autowired
    DelayJobService delayJobService;

    /**
     * mq消息发送
     * @param request
     * @return
     */
    @Override
    public BaseResponse<MqSendResponse> send(@Valid MqSendDTO request) {
        Boolean sendFlag = commonProducerService.send(request);
        MqSendResponse mqSendResponse = new MqSendResponse();
        mqSendResponse.setSendFlag(sendFlag);
        return BaseResponse.success(mqSendResponse);
    }

    /**
     * mq延迟消息发送
     * @param request
     * @return
     */
    @Override
    public BaseResponse<MqSendResponse> sendDelay(@Valid MqSendDelayDTO request) {
        Boolean sendFlag = commonProducerService.sendDelay(request);
        MqSendResponse mqSendResponse = new MqSendResponse();
        mqSendResponse.setSendFlag(sendFlag);
        return BaseResponse.success(mqSendResponse);
    }

    @Override
    public BaseResponse<MqSendResponse> delayProcess() {
        delayJobService.process();
        return BaseResponse.SUCCESSFUL();
    }
}
