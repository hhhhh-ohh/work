package com.wanmi.sbc.customer.api.request.mqconsumer;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.Data;

/**
 * @author lvzhenwei
 * @className CustomerMqConsumerRequest
 * @description mq消费request
 * @date 2021/8/16 10:53 上午
 **/
@Data
public class CustomerMqConsumerRequest extends BaseRequest {

    /**
     * mq消息内容
     */
    private String mqContentJson;

}
