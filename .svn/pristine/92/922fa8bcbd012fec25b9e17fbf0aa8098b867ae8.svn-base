package com.wanmi.sbc.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelWithRightsResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * @author yang
 * @since 2019/3/5
 */
@RestController
@Validated
@RequestMapping("/customer")
@Tag(name = "CustomerLevelRightsBaseController", description = "S2B web公用-客户等级权益API")
public class CustomerLevelRightsBaseController {

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Operation(summary = "查询平台等级权益相关信息")
    @RequestMapping(value = "/level/rightsList", method = RequestMethod.GET)
    public BaseResponse<CustomerLevelWithRightsResponse> queryCustomerLevelRightsList() {
        return BaseResponse.success(customerLevelQueryProvider.listCustomerLevelRightsInfo().getContext());
    }
}
