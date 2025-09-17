package com.wanmi.sbc.customer.api.provider.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.customer.*;
import com.wanmi.sbc.customer.api.response.customer.CustomerAccountModifyResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerAddResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerBatchAddResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerCheckStateModifyResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerModifyResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomersDeleteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(value = "${application.customer.name}", contextId = "CustomerProvider")
public interface CustomerProvider {
    /**
     * 审核客户状态
     *
     * @param request {@link CustomerCheckStateModifyRequest}
     * @return {@link CustomerCheckStateModifyResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/modify-customer-check-state")
    BaseResponse<CustomerCheckStateModifyResponse> modifyCustomerCheckState(@RequestBody @Valid
                                                                                    CustomerCheckStateModifyRequest request);
    /**
     * 审核企业会员
     *
     * @param request {@link CustomerEnterpriseCheckStateModifyRequest}
     * @return {@link CustomerEnterpriseCheckStateModifyRequest}
     */
    @PostMapping("/customer/${application.customer.version}/customer/modify-enterprise-check-state")
    BaseResponse<CustomerCheckStateModifyResponse> checkEnterpriseCustomer(@RequestBody @Valid
                                                                                     CustomerEnterpriseCheckStateModifyRequest request);

    /**
     * boss批量删除会员
     * 删除会员
     * 删除会员详情表
     *
     * @param request {@link CustomersDeleteRequest}
     * @return {@link CustomersDeleteResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/delete-customers")
    BaseResponse<CustomersDeleteResponse> deleteCustomers(@RequestBody @Valid CustomersDeleteRequest request);

    /**
     * 批量添加开放平台会员
     *
     * @param requestList {@link CustomerBatchAddRequest}
     * @return {@link CustomerBatchAddResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/batch-save-customers")
    BaseResponse<CustomerBatchAddResponse> batchSaveCustomer(@RequestBody @Valid CustomerBatchAddRequest requestList);

    /**
     * 新增客户共通
     *
     * @param request {@link CustomerAddRequest}
     * @return {@link CustomerAddResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/save-customer")
    BaseResponse<CustomerAddResponse> saveCustomer(@RequestBody @Valid CustomerAddRequest request);

    /**
     * Boss端修改会员
     *
     * @param request {@link CustomerModifyRequest}
     * @return  {@link BaseResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/modify-customer")
    BaseResponse<CustomerModifyResponse> modifyCustomer(@RequestBody @Valid CustomerModifyRequest request);

    /**
     * 修改绑定手机号
     *
     * @param request {@link CustomerAccountModifyRequest}
     * @return {@link CustomerAccountModifyResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/modify-customer-account")
    BaseResponse<CustomerAccountModifyResponse> modifyCustomerAccount(@RequestBody @Valid CustomerAccountModifyRequest request);

    /**
     * 修改已有的业务员
     *
     * @param request {@link CustomerSalesManModifyRequest}
     * @return {@link BaseResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/modify-customer-sales-man")
    BaseResponse modifyCustomerSalesMan(@RequestBody @Valid CustomerSalesManModifyRequest request);


    /**
     * 审核客户状态
     *
     * @param request {@link CustomerCheckStateModifyRequest}
     * @return {@link CustomerCheckStateModifyResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/modify-to-distributor")
    BaseResponse modifyToDistributor(@RequestBody @Valid CustomerToDistributorModifyRequest request);

    /**
     * 0点执行登录次数清0操作
     *
     */
    @PostMapping("/customer/${application.customer.version}/customer/modify-login-error-time")
    BaseResponse modifyLoginErrorTime();

    /**
     * 用户更换头像
     * @param request
     * @return
     */
    @PostMapping("/customer/${application.customer.version}/customer/edit-head-img")
    BaseResponse editHeadImg(@RequestBody @Valid EditHeadImgRequest request);

    /**
     * 修改客户注销状态
     *
     */
    @PostMapping("/customer/${application.customer.version}/customer/modify-logoutStatus")
    BaseResponse modifyLogOutStatusAndReason(@RequestBody CustomerLogoutStatusModifyRequest request);

    /**
     * 修改客户注销状态
     *
     */
    @PostMapping("/customer/${application.customer.version}/customer/customer-refresh")
    BaseResponse customerRefresh();



    /**
     * 修改新人状态
     * @param request
     * @return
     */
    @PostMapping("/customer/${application.customer.version}/customer/modify-new-customer-state")
    BaseResponse modifyNewCustomerState(@RequestBody @Valid CustomerAccountModifyStateRequest request);
}
