package com.wanmi.sbc.message.notice;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.MessageMQRequest;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.mq.MessageSendProducer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zy
 * @className NoticeService
 * @description 消息通知Service
 * @date 2021/4/27 17:25
 */
@Service
public class NoticeService {

    @Resource
    private MessageSendProducer messageSendProducer;


    /**
     * 发送消息
     *
     * @param nodeType      节点类型
     * @param nodeValue     节点枚举值
     * @param nodeCode      节点code
     * @param sendParams    发送参数（可选）
     * @param customerId    用户ID(Employee表主键)
     * @param mobile        手机号码
     * @param sendSms       是否发送短信
     */
    public void sendMessage(Integer nodeType, Integer nodeValue, String nodeCode,
                           String sendParams, String customerId,String mobile,Integer sendSms) {
        sendMessage(nodeType, nodeValue, nodeCode, null, null, sendParams, customerId, null, mobile, sendSms);
    }

    /**
     * 发送消息
     *
     * @param nodeType      节点类型
     * @param nodeValue     节点枚举值
     * @param nodeCode      节点code
     * @param orderNo       订单号（可选）
     * @param skuId         SKU编码(可选)
     * @param sendParams    发送参数（可选）
     * @param customerId    用户ID(Employee表主键)
     * @param pic           消息图片（可选）
     * @param mobile        手机号码
     */
    public void sendMessage(Integer nodeType, Integer nodeValue, String nodeCode,
                            String orderNo, String skuId, String sendParams, String customerId,
                            String pic, String mobile) {
        sendMessage(nodeType, nodeValue, nodeCode, orderNo, skuId, sendParams, customerId, pic, mobile, Constants.yes);
    }

    /**
     * 发送消息
     *
     * @param nodeType      节点类型
     * @param nodeValue     节点枚举值
     * @param nodeCode      节点code
     * @param orderNo       订单号（可选）
     * @param skuId         SKU编码(可选)
     * @param sendParams    发送参数（可选）
     * @param customerId    用户ID(Employee表主键)
     * @param pic           消息图片（可选）
     * @param mobile        手机号码
     * @param sendSms       是否发送短信
     */
    public void sendMessage(Integer nodeType, Integer nodeValue, String nodeCode,
                            String orderNo, String skuId, String sendParams, String customerId,
                            String pic, String mobile, Integer sendSms) {

        Map<String, Object> routeParam = new HashMap<>(4);
        routeParam.put("type", nodeType);
        routeParam.put("node", nodeValue);
        if (Objects.nonNull(orderNo)) {
            routeParam.put("id", orderNo);
        }
        if (Objects.nonNull(skuId)) {
            routeParam.put("skuId", skuId);
        }

        MessageMQRequest mqRequest = new MessageMQRequest();
        mqRequest.setNodeType(nodeType);
        mqRequest.setNodeCode(nodeCode);
        if (StringUtils.isNotBlank(sendParams)) {
            mqRequest.setParams(Lists.newArrayList(sendParams));
        }
        mqRequest.setRouteParam(routeParam);
        mqRequest.setCustomerId(customerId);
        mqRequest.setPic(pic);
        mqRequest.setMobile(mobile);
        mqRequest.setSendSms(sendSms);
        messageSendProducer.sendMessage(mqRequest);
    }
}
