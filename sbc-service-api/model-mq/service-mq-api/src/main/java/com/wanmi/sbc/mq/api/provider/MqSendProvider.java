package com.wanmi.sbc.mq.api.provider;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.mq.api.response.MqSendResponse;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;


/**
 * @author zhanggaolei
 * @className MqSendProvider
 * @description
 * @date 2021/8/5 4:48 下午
 */
@FeignClient(value = "${application.mq.name}", contextId = "MqSendProvider")
public interface MqSendProvider {

    @PostMapping("/mq/${application.mq.version}/send")
    BaseResponse<MqSendResponse> send(@RequestBody @Valid MqSendDTO request);

    @PostMapping("/mq/${application.mq.version}/send-delay")
    BaseResponse<MqSendResponse> sendDelay(@RequestBody @Valid MqSendDelayDTO request);

    @PostMapping("/mq/${application.mq.version}/delay-process")
    BaseResponse<MqSendResponse> delayProcess();
}
