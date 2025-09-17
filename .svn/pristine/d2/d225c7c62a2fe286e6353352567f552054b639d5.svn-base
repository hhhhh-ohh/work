package com.wanmi.sbc.message.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.StoreMessageMQRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @description 管理端，商家消息发送服务
 * @author malianfeng
 * @date 2022/7/12 14:59
 */
@Slf4j
@Service
public class StoreMessageService {

    @Autowired private MqSendProvider mqSendProvider;

    /**
     * 转换并发送异步消息
     * @param receiveStoreId    接收消息的商家id
     * @param nodeCode          节点标识
     * @param contentParams     内容参数
     * @param routeParams       路由参数
     */
    public void convertAndSend(Long receiveStoreId, String nodeCode, List<Object> contentParams, Map<String, Object> routeParams) {
        log.info("处理商家消息发送开始，节点标识:{}, 内容参数:{}, 路由参数:{}", nodeCode, contentParams, routeParams);
        try {
            // 1. 参数封装请求与
            StoreMessageMQRequest request = this.paramProcess(receiveStoreId, nodeCode, contentParams, routeParams);
            // 2. 执行消息发送
            this.send(request);
        } catch (Exception e) {
            log.info("处理商家消息发送失败，节点标识:{}, 内容参数:{}, 路由参数:{}", nodeCode, contentParams, routeParams, e);
        }
    }

    /**
     * 消息参数封装
     */
    private StoreMessageMQRequest paramProcess(Long receiveStoreId, String nodeCode, List<Object> contentParams, Map<String, Object> routeParams) {
        StoreMessageMQRequest messageRequest = new StoreMessageMQRequest();
        messageRequest.setStoreId(receiveStoreId);
        messageRequest.setNodeCode(nodeCode);
        messageRequest.setProduceTime(LocalDateTime.now());
        messageRequest.setContentParams(contentParams);
        messageRequest.setRouteParams(routeParams);
        return messageRequest;
    }

    /**
     * 执行发送
     */
    private void send(StoreMessageMQRequest request) {
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setData(JSONObject.toJSONString(request));
        mqSendDTO.setTopic(ProducerTopic.STORE_MESSAGE_SEND);
        mqSendProvider.send(mqSendDTO);
    }
}

