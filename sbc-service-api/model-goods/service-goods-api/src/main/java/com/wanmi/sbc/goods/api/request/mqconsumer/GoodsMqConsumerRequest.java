package com.wanmi.sbc.goods.api.request.mqconsumer;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lvzhenwei
 * @className GoodsMqConsumerRequest
 * @description mq消费者request
 * @date 2021/8/11 3:44 下午
 **/
@Data
public class GoodsMqConsumerRequest extends BaseRequest {

    /**
     * mq消息内容
     */
    private String mqContentJson;
}
