package com.wanmi.sbc.dbreplay.kafka.canal;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.dbreplay.bean.canal.CanalData;
import com.wanmi.sbc.dbreplay.service.canal.ReplayService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-8-16
 * \* Time: 15:45
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Component
public class CanalConsumer {
    protected static final Logger logger = LoggerFactory.getLogger(CanalConsumer.class);
    @Autowired
    private ReplayService replayService;

    /**
     * 监听kafka topic
     * @param record
     */
    @KafkaListener(topics = {"#{'${topics.canal}'.replace('.','${topics.canal.replay:.}').split(',')}"},concurrency = "${topics.canal.concurrency}")
    public void listenExample(ConsumerRecord<String, String> record) {
        logger.info("in:" + record.topic());
        logger.debug(record.value());
        String message = record.value();
        CanalData data = JSONObject.parseObject(message, CanalData.class);
        replayService.replay(data);
    }
}
