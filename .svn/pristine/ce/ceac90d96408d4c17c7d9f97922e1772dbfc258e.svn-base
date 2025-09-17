package com.wanmi.sbc.empower.api.provider.logisticssetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.logisticssetting.DeliveryQueryRequest;
import com.wanmi.sbc.empower.api.response.logisticssetting.DeliveryQueryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Author: songhanlin
 * @Date: Created In 下午4:01 2021/4/12
 * @Description: TODO
 */
@FeignClient(value = "${application.empower.name}",contextId = "ExpressQueryProvider")
public interface ExpressQueryProvider {

    /**
     * @description 查询物流信息
     * @author  songhanlin
     * @date: 2021/4/13 上午11:26
     * @param request
     * @return com.wanmi.sbc.empower.api.response.logisticssetting.DeliveryQueryResponse
     **/
    @PostMapping("/sms/${application.empower.version}/express/query")
    BaseResponse<DeliveryQueryResponse> expressInfoQuery(@RequestBody @Valid DeliveryQueryRequest request);

//    @PostMapping("/sms/${application.empower.version}/express/subscribe")
//    DeliverySubscribeResponse expressSubscribe(@RequestBody @Valid DeliveryQueryRequest request);

}
