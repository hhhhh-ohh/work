package com.wanmi.sbc.mq.message;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.base.Splitter;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.crm.api.provider.customgrouprel.CustomGroupRelProvide;
import com.wanmi.sbc.crm.api.request.crmgroup.CrmGroupRequest;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerListByConditionRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerListByConditionResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.message.api.provider.messagesend.MessageSendProvider;
import com.wanmi.sbc.message.api.provider.pushsend.PushSendProvider;
import com.wanmi.sbc.message.api.request.messagesend.MessageSendAddRequest;
import com.wanmi.sbc.message.api.request.pushsend.PushSendAddRequest;
import com.wanmi.sbc.message.api.response.messagesend.MessageSendAddResponse;
import com.wanmi.sbc.message.api.response.pushsend.PushSendAddResponse;
import com.wanmi.sbc.message.bean.enums.MessageSendType;
import com.wanmi.sbc.message.bean.enums.MessageType;
import com.wanmi.sbc.message.bean.enums.PushFlag;
import com.wanmi.sbc.message.bean.enums.SendType;
import com.wanmi.sbc.message.bean.vo.PushSendVO;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import com.wanmi.sbc.mq.producer.CommonProducerService;

import io.seata.spring.annotation.GlobalTransactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: sbc-micro-service
 * @description: 新增push消息
 * @create: 2020-01-14 11:33
 **/
@Service
public class PushSendService {

    @Autowired
    private PushSendProvider pushSendProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomGroupRelProvide customGroupRelProvide;

    @Autowired
    private CommonProducerService commonProducerService;

    @Autowired
    private MessageSendProvider messageSendProvider;

    @GlobalTransactional
    public BaseResponse<PushSendAddResponse> add(PushSendAddRequest addReq) {
        List<String> customerIds = this.getCustomerIds(addReq.getMsgRecipient(), addReq.getMsgRecipientDetail());
        addReq.setCustomers(customerIds);
        addReq.setDelFlag(DeleteFlag.NO);
        PushSendAddResponse pushSendAddResponse = pushSendProvider.add(addReq).getContext();
        MessageSendAddRequest messageSendAddRequest = new MessageSendAddRequest();
        messageSendAddRequest.setPushId(pushSendAddResponse.getPushSendVO().getId().toString());
        messageSendAddRequest.setContent(addReq.getMsgContext());
        messageSendAddRequest.setImgUrl(addReq.getMsgImg());
        messageSendAddRequest.setPushFlag(addReq.getPushFlag());
        messageSendAddRequest.setName(addReq.getMsgName());
        messageSendAddRequest.setMessageType(MessageType.Preferential);
        messageSendAddRequest.setSendTimeType(SendType.NOW);
        messageSendAddRequest.setSendTime(LocalDateTime.now());
        if (addReq.getPushTime() != null){
            messageSendAddRequest.setSendTimeType(SendType.DELAY);
            messageSendAddRequest.setSendTime(addReq.getPushTime());
        }
        messageSendAddRequest.setSendType(MessageSendType.fromValue(addReq.getMsgRecipient()));
        messageSendAddRequest.setTitle(addReq.getMsgTitle());
        if (Objects.nonNull(addReq.getMsgRecipientDetail())){
            messageSendAddRequest.setJoinIds(Arrays.asList(addReq.getMsgRecipientDetail().split(",")));
        }
        if (PushFlag.OPERATION_PLAN.equals(addReq.getPushFlag())){
            messageSendAddRequest.setPlanId(addReq.getPlanId());
            this.query(pushSendAddResponse.getPushSendVO());
        }
        messageSendAddRequest.setRouteParams(addReq.getMsgRouter());
        messageSendAddRequest.setPcRouteParams(addReq.getPcMsgRouter());
        MessageSendAddResponse messageSendAddResponse = messageSendProvider.add(messageSendAddRequest).getContext();
        if(messageSendAddRequest.getSendTimeType() == SendType.NOW){
            //发送异步消息处理站内信发送
            MqSendDelayDTO mqSendDelayDTO = new MqSendDelayDTO();
            mqSendDelayDTO.setTopic(ProducerTopic.APP_MESSAGE);
            mqSendDelayDTO.setData(messageSendAddResponse.getMessageSendVO().getMessageId().toString());
            mqSendDelayDTO.setDelayTime(6000L);
            commonProducerService.sendDelay(mqSendDelayDTO);
        }
        return BaseResponse.success(pushSendAddResponse);
    }

    private List<String> getCustomerIds(int msgRecipient, String msgRecipientDetail){
        List<String> customerIds = null;
        switch (msgRecipient){
            case 1:
                String[] detail = msgRecipientDetail.split(",");
                List<Long> levelIds = new ArrayList<>(detail.length);
                for (String levelId : detail){
                    levelIds.add(Long.valueOf(levelId));
                }
                CustomerListByConditionResponse response =
                        customerQueryProvider.listCustomerByCondition(CustomerListByConditionRequest.builder()
                                .customerLevelIds(levelIds).build()).getContext();
                customerIds =
                        response.getCustomerVOList().stream().map(CustomerVO::getCustomerId).collect(Collectors.toList());
                break;
            case 2:
                break;
            case 3:
                List<String> result = Splitter.on(",").trimResults().splitToList(msgRecipientDetail);
                // 系统人群ID
                List<Long> systemGroupId =
                        result.stream().filter(i -> i.startsWith("0_"))
                                .map(i-> Long.valueOf(i.replace("0_", ""))).collect(Collectors.toList());
                // 自定义人群ID
                List<Long> groupId =
                        result.stream().filter(i -> i.startsWith("1_"))
                                .map(i-> Long.valueOf(i.replace("1_", ""))).collect(Collectors.toList());
                customerIds = customGroupRelProvide.queryListByGroupIds(CrmGroupRequest.builder()
                        .customGroupList(groupId).sysGroupList(systemGroupId).build()).getContext();

                break;
            case 4:
                customerIds = Splitter.on(",").trimResults().splitToList(msgRecipientDetail);
                break;
            default:
                break;
        }
        return customerIds;
    }

    public void query(PushSendVO pushSendVO){
        MqSendDelayDTO mqSendDelayDTO = new MqSendDelayDTO();
        mqSendDelayDTO.setTopic(ProducerTopic.SMS_PUSH_QUERY);
        mqSendDelayDTO.setData(JSONObject.toJSONString(pushSendVO));
        mqSendDelayDTO.setDelayTime(600000L);
        commonProducerService.sendDelay(mqSendDelayDTO);
    }
}