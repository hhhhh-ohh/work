package com.wanmi.sbc.message.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.provider.messagesend.MessageSendProvider;
import com.wanmi.sbc.message.api.request.messagesend.MessageSendAddRequest;
import com.wanmi.sbc.message.api.request.messagesend.MessageSendDelByIdRequest;
import com.wanmi.sbc.message.api.request.messagesend.MessageSendModifyRequest;
import com.wanmi.sbc.message.api.response.messagesend.MessageSendAddResponse;
import com.wanmi.sbc.message.api.response.messagesend.MessageSendModifyResponse;
import com.wanmi.sbc.message.bean.enums.SendType;
import com.wanmi.sbc.message.bean.vo.MessageSendVO;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;

import io.seata.spring.annotation.GlobalTransactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: sbc-micro-service
 * @description: 创建站内信
 * @create: 2020-01-13 10:08
 **/
@Service
public class MessageService {

    @Autowired
    private MessageSendProvider messageSendProvider;

    @Autowired
    private MqSendProvider mqSendProvider;


    public BaseResponse addMessage(MessageSendAddRequest request){
        BaseResponse<MessageSendAddResponse> response = messageSendProvider.add(request);
        MessageSendVO messageSendVO = response.getContext().getMessageSendVO();
        if(request.getSendTimeType() == SendType.NOW){
            //发送异步消息处理站内信发送
            MqSendDelayDTO mqSendDelayDTO = new MqSendDelayDTO();
            mqSendDelayDTO.setTopic(ProducerTopic.APP_MESSAGE);
            mqSendDelayDTO.setData(messageSendVO.getMessageId().toString());
            mqSendDelayDTO.setDelayTime(6L*1000);
            mqSendProvider.sendDelay(mqSendDelayDTO);
        }
//        if (CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())) {
//            addOrModifyTaskJob(messageSendVO);
//        }
        return BaseResponse.SUCCESSFUL();
    }


    @GlobalTransactional
    public BaseResponse modify(MessageSendModifyRequest request){
        BaseResponse<MessageSendModifyResponse> response = messageSendProvider.modify(request);
        return BaseResponse.SUCCESSFUL();
    }

    public BaseResponse deleteById(MessageSendDelByIdRequest request){
        messageSendProvider.deleteById(request);
        return BaseResponse.SUCCESSFUL();
    }
}