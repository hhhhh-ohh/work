package com.wanmi.sbc.crm.customerplanconversion;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.crm.api.provider.customerplanconversion.CustomerPlanConversionQueryProvider;
import com.wanmi.sbc.crm.api.request.customerplanconversion.CustomerPlanConversionByPlanIdRequest;
import com.wanmi.sbc.crm.api.response.customerplanconversion.CustomerPlanConversionByIdResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

/**
 * @author zhangwenchang
 */
@Tag(name =  "运营计划转化效果管理API", description =  "CustomerPlanConversionController")
@RestController
@Validated
@RequestMapping(value = "/customer/plan/conversion")
public class CustomerPlanConversionController {

    @Autowired
    private CustomerPlanConversionQueryProvider customerPlanConversionQueryProvider;

    @Operation(summary = "根据id查询运营计划转化效果")
    @GetMapping("/{planId}")
    public BaseResponse<CustomerPlanConversionByIdResponse> getById(@PathVariable Long planId) {
        if (planId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomerPlanConversionByPlanIdRequest idReq = new CustomerPlanConversionByPlanIdRequest();
        idReq.setPlanId(planId);
        return customerPlanConversionQueryProvider.getByPlanId(idReq);
    }

}
