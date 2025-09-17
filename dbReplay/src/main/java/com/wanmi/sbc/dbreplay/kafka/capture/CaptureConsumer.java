package com.wanmi.sbc.dbreplay.kafka.capture;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.dbreplay.bean.capture.OplogData;
import com.wanmi.sbc.dbreplay.service.capture.MongoMappingReplayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2019-08-16 13:47
 */
@Component
@Slf4j
public class CaptureConsumer {
    @Value("${mongo.capture.mapping.enable:false}")
    private Boolean mappingFlag;
    @Autowired
    private MongoMappingReplayService mappingReplayService;


    @KafkaListener(topics = {"#{'${topics.capture}'.split(',')}"},concurrency = "${topics.capture.concurrency}")
    public void listenOmsNewStake(ConsumerRecord<String, String> record) {
        String message = record.value();
        log.debug("in kafka topic :{},mongo data :{}",record.topic(),message);
        OplogData data = JSONObject.parseObject(message, OplogData.class);

        mappingReplayService.replay(data);

    }

}
