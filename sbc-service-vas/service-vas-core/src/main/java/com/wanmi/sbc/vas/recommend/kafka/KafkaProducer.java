package com.wanmi.sbc.vas.recommend.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author 10486
 * @Date 17:07 2020/12/2
 **/
@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;


    public void send(String message, String topic){
        log.info("======发送kafka消息=====>topic:{}=====>message:{}======", topic, message);
        kafkaTemplate.send(topic, message);
    }

}
