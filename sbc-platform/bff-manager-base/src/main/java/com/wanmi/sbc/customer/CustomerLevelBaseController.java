package com.wanmi.sbc.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelListBaseResponse;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelListResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@Tag(name =  "会员等级API", description =  "CustomerLevelController")
@RestController
@Validated
@RequestMapping("/customer")
public class CustomerLevelBaseController {

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    /**
     * 查询boss所有会员等级
     *
     * @returnF
     */
    @Operation(summary = "查询boss所有会员等级")
    @RequestMapping(value = "/levellist", method = RequestMethod.GET)
    public BaseResponse<CustomerLevelListResponse> findAllLevel() {
        return customerLevelQueryProvider.listAllCustomerLevel();
    }

    /**
     * 查询boss所有会员等级
     *
     * @returnF
     */
    @Operation(summary = "查询boss所有会员等级")
    @RequestMapping(value = "/find-all-level", method = RequestMethod.GET)
    public BaseResponse<CustomerLevelListBaseResponse> findAllLevelNew() {
        return customerLevelQueryProvider.listAllCustomerLevelNew();
    }
}
