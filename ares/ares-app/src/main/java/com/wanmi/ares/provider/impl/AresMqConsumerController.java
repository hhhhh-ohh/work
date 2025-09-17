package com.wanmi.ares.provider.impl;

import com.wanmi.ares.base.BaseResponse;
import com.wanmi.ares.provider.AresMqConsumerProvider;
import com.wanmi.ares.request.mq.MqConsumerRequest;
import com.wanmi.ares.source.mq.CustomerMessageConsumer;
import com.wanmi.ares.source.mq.ExportDataRequestConsumer;
import com.wanmi.ares.source.mq.MessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author lvzhenwei
 * @className MqConsumerController
 * @description mq消费对应方法实现
 * @date 2021/8/11 1:40 下午
 **/
@Slf4j
@RestController
public class AresMqConsumerController implements AresMqConsumerProvider {

    @Autowired
    private ExportDataRequestConsumer exportDataRequestConsumer;

    @Autowired
    private MessageConsumer messageConsumer;

    @Autowired
    private CustomerMessageConsumer customerMessageConsumer;

    @Override
    public BaseResponse flowCustomerSync(@Valid MqConsumerRequest mqConsumerRequest) {
        messageConsumer.flowSynchronization(mqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse flowMarketingSync(@Valid MqConsumerRequest mqConsumerRequest) {
        messageConsumer.flowMarketing(mqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse customerFirstPay(@Valid MqConsumerRequest mqConsumerRequest) {
        customerMessageConsumer.customerFirstPay(mqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse produceExportData(@Valid MqConsumerRequest mqConsumerRequest) {
        exportDataRequestConsumer.produceExportData(mqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }
}
