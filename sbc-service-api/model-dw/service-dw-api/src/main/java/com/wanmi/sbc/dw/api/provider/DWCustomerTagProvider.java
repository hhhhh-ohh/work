package com.wanmi.sbc.dw.api.provider;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.dw.api.request.CustomerPreferenceTagRequest;
import com.wanmi.sbc.dw.api.request.CustomerTagRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(value = "${application.dw.name}", contextId = "DWCustomerTagProvider")
public interface DWCustomerTagProvider {

    /**
     * 获取会员标签
     * @param request
     * @return
     */
    @PostMapping("/dw/${application.dw.version}/tag/customer-tag")
    BaseResponse getCustomerTag(@RequestBody @Valid CustomerTagRequest request);

    /**
     * 获取会员偏好类标签
     * @param request
     * @return
     */
    @PostMapping("/dw/${application.dw.version}/tag/customer-preference-tag")
    BaseResponse getCustomerPreferenceTag(@RequestBody @Valid CustomerPreferenceTagRequest request);
}
