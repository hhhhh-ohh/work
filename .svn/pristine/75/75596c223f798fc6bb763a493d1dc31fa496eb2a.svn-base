package com.wanmi.ares.provider;

import com.wanmi.ares.base.BaseResponse;
import com.wanmi.ares.request.mq.MqConsumerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description mq消费者方法接口
 * @author  lvzhenwei
 * @date 2021/8/11 1:36 下午
 **/
@FeignClient(name = "${application.ares.name}", contextId="AresMqConsumerProvider")
public interface AresMqConsumerProvider {

    /**
     * @description mq消费，同步流量数据接口
     * @author  lvzhenwei
     * @date 2021/8/11 1:39 下午
     * @return void
     **/
    @PostMapping("/ares/${application.ares.version}/mq/flow-customer-sync")
    BaseResponse flowCustomerSync(@RequestBody @Valid MqConsumerRequest mqConsumerRequest);

    /**
     * @description mq消费，同步营销埋点流量数据接口
     * @author  lvzhenwei
     * @date 2021/8/11 1:39 下午
     * @return void
     **/
    @PostMapping("/ares/${application.ares.version}/mq/flow-marketing-sync")
    BaseResponse flowMarketingSync(@RequestBody @Valid MqConsumerRequest mqConsumerRequest);

    @PostMapping("/ares/${application.ares.version}/mq/customer-first-pay")
    BaseResponse customerFirstPay(@RequestBody @Valid MqConsumerRequest mqConsumerRequest);

    @PostMapping("/ares/${application.ares.version}/mq/produce-export-data")
    BaseResponse produceExportData(@RequestBody @Valid MqConsumerRequest mqConsumerRequest);





}
