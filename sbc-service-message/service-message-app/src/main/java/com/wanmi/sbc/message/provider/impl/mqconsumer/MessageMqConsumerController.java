package com.wanmi.sbc.message.provider.impl.mqconsumer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.provider.mqconsumer.MessageMqConsumerProvider;
import com.wanmi.sbc.message.api.request.mqconsumer.MessageMqConsumerRequest;
import com.wanmi.sbc.message.mqconsumer.MessageMqConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author lvzhenwei
 * @className MessageMqConsumerController
 * @description mq消费者方法实现
 * @date 2021/8/11 3:23 下午
 **/
@RestController
public class MessageMqConsumerController implements MessageMqConsumerProvider {

    @Autowired
    private MessageMqConsumerService messageMqConsumerService;

    @Override
    public BaseResponse smsMessageSend(@Valid MessageMqConsumerRequest request) {
        messageMqConsumerService.smsMessageSend(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse smsPushQuery(@Valid MessageMqConsumerRequest request) {
        messageMqConsumerService.smsPushQuery(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse smsSendMessageCode(@Valid MessageMqConsumerRequest request) {
        messageMqConsumerService.smsSendMessageCode(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse smsSendMessage(@Valid MessageMqConsumerRequest request) {
        messageMqConsumerService.smsSendMessage(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse storeNoticeSend(@Valid MessageMqConsumerRequest request) {
        messageMqConsumerService.storeNoticeSend(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse storeMessageSend(@Valid MessageMqConsumerRequest request) {
        messageMqConsumerService.storeMessageSend(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse sendMiniProgramSubscribeMessage(@Valid MessageMqConsumerRequest request) {
        messageMqConsumerService.dealMiniProgramSubscribeMsg(request.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

}
