package com.wanmi.sbc.mq.producer;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.mq.bean.constants.CommonProperties;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import com.wanmi.sbc.mq.delay.DelayProperties;
import com.wanmi.sbc.mq.delay.service.DelayService;
import com.wanmi.tools.logtrace.core.constant.TraceConstants;
import com.wanmi.tools.logtrace.core.context.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author zhanggaolei
 * @className CommonProviderService
 * @description TODO 如果Spring cloud stream 升级到3.1则该生产类型废弃，请使用streamBridge来做生产
 * @date 2021/8/5 3:24 下午
 */
@Service
public class CommonProducerService {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired private DelayProperties delayProperties;

    @Autowired private DelayService delayService;

    /**
     * 发送数据
     * @description
     * @author  zhanggaolei
     * @date 2021/8/6 10:00 上午
     * @param sendDTO
     * @return void
     **/
    public Boolean send(MqSendDTO sendDTO) {
        String stringData;
        if(sendDTO.getData() instanceof String){
            stringData = (String) sendDTO.getData();
        } else {
            stringData = JSONObject.toJSONString(sendDTO.getData());
        }
        Boolean sendFlag = streamBridge.send(sendDTO.getTopic().concat(CommonProperties.OUTPUT_SUFFIX),
                org.springframework.messaging.support.MessageBuilder.withPayload(stringData)
                        .setHeader("contentType", "text/plain;charset=UTF-8")
                        .setHeader(TraceConstants.KEY_TRACE_ID, TraceContext.getTraceId())
                        .setHeader(TraceConstants.KEY_SPAN_ID, TraceContext.generateNextSpanId())
                        .build());
        return sendFlag;
    }

    public Boolean send(MqSendDTO sendDTO,String traceId) {
        String stringData;
        if (sendDTO.getData() instanceof String) {
            stringData = (String) sendDTO.getData();
        } else {
            stringData = com.alibaba.fastjson2.JSONObject.toJSONString(sendDTO.getData());
        }
        Boolean sendFlag = streamBridge.send(sendDTO.getTopic().concat(CommonProperties.OUTPUT_SUFFIX),
                org.springframework.messaging.support.MessageBuilder.withPayload(stringData)
                        .setHeader("contentType", "text/plain;charset=UTF-8")
                        .setHeader(TraceConstants.KEY_TRACE_ID, traceId)
                        .build());
        return sendFlag;
    }

    /**
     * 带有延迟功能的发送
     * @description
     * @author  zhanggaolei
     * @date 2021/8/6 10:00 上午
     * @param sendDelayDTO
     * @return void
     **/
    public Boolean sendDelay(MqSendDelayDTO sendDelayDTO) {
        if (delayProperties.getIsLocal()) {
            return delayService.add(sendDelayDTO);
        } else {
            String stringData;
            if (sendDelayDTO.getData() instanceof String) {
                stringData = (String) sendDelayDTO.getData();
            } else {
                stringData = com.alibaba.fastjson2.JSONObject.toJSONString(sendDelayDTO.getData());
            }

            Boolean sendFlag = streamBridge.send(sendDelayDTO.getTopic().concat(CommonProperties.OUTPUT_SUFFIX),
                    MessageBuilder.withPayload(stringData)
                            .setHeader("contentType", "text/plain;charset=UTF-8")
                            .setHeader("x-delay", sendDelayDTO.getDelayTime())
                            .setHeader(TraceConstants.KEY_TRACE_ID, TraceContext.getTraceId())
                            .setHeader(TraceConstants.KEY_SPAN_ID, TraceContext.generateNextSpanId())
                            .build());
            return sendFlag;
        }
    }

}
