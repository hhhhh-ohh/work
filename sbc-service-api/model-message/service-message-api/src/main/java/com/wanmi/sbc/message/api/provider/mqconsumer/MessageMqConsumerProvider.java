package com.wanmi.sbc.message.api.provider.mqconsumer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.message.api.request.mqconsumer.MessageMqConsumerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description
 * @author  lvzhenwei
 * @date 2021/8/11 3:19 下午
 **/
@FeignClient(value = "${application.message.name}", contextId = "MessageMqConsumerProvider")
public interface MessageMqConsumerProvider {

    /**
     * @description 消息发送消费者处理
     * @author  lvzhenwei
     * @date 2021/8/11 3:27 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/sms/${application.sms.version}/mq/consumer/sms-message-send")
    BaseResponse smsMessageSend(@RequestBody @Valid MessageMqConsumerRequest request);

    /**
     * @description 运营计划push查询推送详情
     * @author  lvzhenwei
     * @date 2021/8/11 6:14 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/sms/${application.sms.version}/mq/consumer/sms-push-query")
    BaseResponse smsPushQuery(@RequestBody @Valid MessageMqConsumerRequest request);

    /**
     * @description 验证码短信发送
     * @author  lvzhenwei
     * @date 2021/8/16 1:38 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/sms/${application.sms.version}/mq/consumer/sms-send-message-code")
    BaseResponse smsSendMessageCode(@RequestBody @Valid MessageMqConsumerRequest request);

    /**
     * @description 短信发送
     * @author  lvzhenwei
     * @date 2021/8/17 3:58 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/sms/${application.sms.version}/mq/consumer/sms-send-message")
    BaseResponse smsSendMessage(@RequestBody @Valid MessageMqConsumerRequest request);

    /**
     * @description 商家公告发送
     * @author malianfeng
     * @date 2022/7/13 11:33
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/sms/${application.sms.version}/mq/consumer/store-notice-send")
    BaseResponse storeNoticeSend(@RequestBody @Valid MessageMqConsumerRequest request);

    /**
     * @description 商家信息发送
     * @author malianfeng
     * @date 2022/7/13 11:33
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/sms/${application.sms.version}/mq/consumer/store-message-send")
    BaseResponse storeMessageSend(@RequestBody @Valid MessageMqConsumerRequest request);

    /**
     * @description 消息发送消费者处理
     * @author  xufeng
     * @date 2022/8/19 3:27 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/sms/${application.sms.version}/mq/consumer/mini-program-subscribe-message-send")
    BaseResponse sendMiniProgramSubscribeMessage(@RequestBody @Valid MessageMqConsumerRequest request);
}
