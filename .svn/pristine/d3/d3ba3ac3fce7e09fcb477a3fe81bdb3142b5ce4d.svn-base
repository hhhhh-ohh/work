package com.wanmi.sbc.job;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.message.api.provider.messagesend.MessageSendQueryProvider;
import com.wanmi.sbc.message.api.request.messagesend.MessageSendPageRequest;
import com.wanmi.sbc.message.api.response.messagesend.MessageSendPageResponse;
import com.wanmi.sbc.message.bean.vo.MessageSendVO;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import com.xxl.job.core.handler.annotation.XxlJob;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lvzhenwei
 * @className MessageSendJobHandle
 * @description 发送站内信
 * @date 2021/8/19 4:15 下午
 **/
@Component
@Slf4j
public class MessageSendJobHandle {

    @Autowired
    private MessageSendQueryProvider messageSendQueryProvider;

    @Autowired
    private MqSendProvider mqSendProvider;

    @XxlJob(value = "MessageSendJobHandle")
    public void execute() throws Exception {
        Integer pageNum = NumberUtils.INTEGER_ZERO;
        Integer pageSize = 100;
        while (true) {
            MessageSendPageRequest messageSendPageReq = new MessageSendPageRequest();
            messageSendPageReq.setPageNum(pageNum);
            messageSendPageReq.setPageSize(pageSize);
            messageSendPageReq.setIsAppMessageSendFlag(true);
            messageSendPageReq.setSendTimeEnd(LocalDateTime.now());
            MessageSendPageResponse messageSendPageResponse = messageSendQueryProvider.page(messageSendPageReq).getContext();
            MicroServicePage<MessageSendVO> microServicePage = messageSendPageResponse.getMessageSendVOPage();
            List<MessageSendVO> messageSendVOList = microServicePage.getContent();
            messageSendVOList.forEach(messageSendVO -> {
                MqSendDelayDTO mqSendDelayDTO = new MqSendDelayDTO();
                mqSendDelayDTO.setTopic(ProducerTopic.APP_MESSAGE);
                mqSendDelayDTO.setData(messageSendVO.getMessageId().toString());
                mqSendDelayDTO.setDelayTime(6 * 1000L);
                mqSendProvider.sendDelay(mqSendDelayDTO);
            });
            if (CollectionUtils.isEmpty(messageSendVOList) || messageSendVOList.size() < pageSize) {
                break;
            }
            pageNum++;
        }
    }
}
