package com.wanmi.sbc.crm.customerplansendcount;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.crm.api.provider.customerplansendcount.CustomerPlanSendCountQueryProvider;
import com.wanmi.sbc.crm.api.request.customerplansendcount.CustomerPlanSendCountByIdRequest;
import com.wanmi.sbc.crm.api.response.customerplansendcount.CustomerPlanSendCountByIdResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(name =  "权益礼包优惠券发放统计表管理API", description =  "CustomerPlanSendCountController")
@RestController
@Validated
@RequestMapping(value = "/customerplansendcount")
public class CustomerPlanSendCountController {

    @Autowired
    private CustomerPlanSendCountQueryProvider customerPlanSendCountQueryProvider;


    @Operation(summary = "根据planId查询权益礼包优惠券发放统计表")
    @GetMapping("/{planId}")
    public BaseResponse<CustomerPlanSendCountByIdResponse> getById(@PathVariable Long planId) {
        if (planId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomerPlanSendCountByIdRequest idReq = new CustomerPlanSendCountByIdRequest();
        idReq.setPlanId(planId);
        return customerPlanSendCountQueryProvider.getByPlanId(idReq);
    }

}
