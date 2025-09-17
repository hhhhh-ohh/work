package com.wanmi.sbc.customer.api.provider.mqconsumer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.mqconsumer.CustomerMqConsumerRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailQueryRequest;
import com.wanmi.sbc.customer.api.response.points.CustomerPointsDetailListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author lvzhenwei
 * @className CustomerMqConsumerProvider
 * @description customer消费接口
 * @date 2021/8/16 10:51 上午
 **/
@FeignClient(value = "${application.customer.name}", contextId = "CustomerMqConsumerProvider")
public interface CustomerMqConsumerProvider {

    /**
     * @description 增加成长值
     * @author  lvzhenwei
     * @date 2021/8/16 10:56 上午
     * @param customerMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/customer/${application.customer.version}/mq/consumer/increase-customer-growth-value")
    BaseResponse increaseCustomerGrowthValue(@RequestBody @Valid CustomerMqConsumerRequest customerMqConsumerRequest);

    /**
     * @description 用户注销
     * @author  xufeng
     * @date 2022/3/31 10:56 上午
     * @param customerMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/customer/${application.customer.version}/mq/consumer/customer-logout")
    BaseResponse customerLogout(@RequestBody @Valid CustomerMqConsumerRequest customerMqConsumerRequest);

    /**
     * @description 批量导入-客户-积分修改
     * @author  shy
     * @date 2023/3/15 3:46 下午
     * @param customerMqConsumerRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/customer/${application.customer.version}/mq/consumer/customer-points-update")
    BaseResponse batchCustomerPointsUpdate (@RequestBody @Valid CustomerMqConsumerRequest customerMqConsumerRequest);

}
