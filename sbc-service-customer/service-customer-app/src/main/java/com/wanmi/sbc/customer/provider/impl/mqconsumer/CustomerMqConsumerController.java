package com.wanmi.sbc.customer.provider.impl.mqconsumer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.mqconsumer.CustomerMqConsumerProvider;
import com.wanmi.sbc.customer.api.request.mqconsumer.CustomerMqConsumerRequest;
import com.wanmi.sbc.customer.mqconsumer.CustomerMqConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author lvzhenwei
 * @className CustomerMqConsumerController
 * @description mq消费接口实现
 * @date 2021/8/16 10:57 上午
 **/
@RestController
public class CustomerMqConsumerController implements CustomerMqConsumerProvider {

    @Autowired
    CustomerMqConsumerService customerMqConsumerService;

    @Override
    public BaseResponse increaseCustomerGrowthValue(@Valid CustomerMqConsumerRequest customerMqConsumerRequest) {
        customerMqConsumerService.increaseCustomerGrowthValue(customerMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse customerLogout(@Valid CustomerMqConsumerRequest customerMqConsumerRequest) {
        return BaseResponse.success(customerMqConsumerService.customerLogout(customerMqConsumerRequest.getMqContentJson()));
    }

    @Override
    public BaseResponse  batchCustomerPointsUpdate (@Valid CustomerMqConsumerRequest customerMqConsumerRequest) {
        customerMqConsumerService.batchCustomerPointsUpdate(customerMqConsumerRequest.getMqContentJson());
        return BaseResponse.SUCCESSFUL();
    }
}
