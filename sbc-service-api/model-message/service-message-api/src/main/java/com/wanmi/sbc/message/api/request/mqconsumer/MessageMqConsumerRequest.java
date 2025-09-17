package com.wanmi.sbc.message.api.request.mqconsumer;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.Data;

/**
 * @author lvzhenwei
 * @className MessageMqConsumerRequest
 * @description mq消费者quest
 * @date 2021/8/11 3:21 下午
 **/
@Data
public class MessageMqConsumerRequest extends BaseRequest {

    /**
     * mq消息内容
     */
    private String mqContentJson;
}
