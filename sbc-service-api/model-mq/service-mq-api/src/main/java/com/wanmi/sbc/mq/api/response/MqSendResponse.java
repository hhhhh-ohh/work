package com.wanmi.sbc.mq.api.response;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;

/**
 * @author lvzhenwei
 * @className MqSendResponse
 * @description mq发送状态
 * @date 2021/8/17 9:52 上午
 **/
@Data
public class MqSendResponse extends BasicResponse {

    /**
     * 发送状态
     */
    private Boolean sendFlag;
}
