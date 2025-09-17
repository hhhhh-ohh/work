package com.wanmi.sbc.customer.provider.impl.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.customer.CustomerProvider;
import com.wanmi.sbc.customer.api.request.customer.*;
import com.wanmi.sbc.customer.api.response.customer.CustomerAccountModifyResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerAddResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerBatchAddResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerCheckStateModifyResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerModifyResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomersDeleteResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailToEsVO;
import com.wanmi.sbc.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@Validated
public class CustomerController implements CustomerProvider {
    @Autowired
    private CustomerService customerService;

    /**
     * 审核客户状态
     *
     * {@link CustomerService#updateCheckState}
     */
    @Override
    public BaseResponse<CustomerCheckStateModifyResponse> modifyCustomerCheckState(@RequestBody @Valid
                                                                                               CustomerCheckStateModifyRequest request) {
        int count = customerService.updateCheckState(request);

        return BaseResponse.success(new CustomerCheckStateModifyResponse(count));
    }

    /**
     * 审核企业会员
     *
     */
    @Override
    public BaseResponse<CustomerCheckStateModifyResponse> checkEnterpriseCustomer(@RequestBody @Valid
                                                                                              CustomerEnterpriseCheckStateModifyRequest request) {
        int count = customerService.checkEnterpriseCustomer(request);

        return BaseResponse.success(new CustomerCheckStateModifyResponse(count));
    }

    /**
     * boss批量删除会员
     * 删除会员
     * 删除会员详情表
     *
     * {@link CustomerService#delete}
     */
    @Override
    public BaseResponse<CustomersDeleteResponse> deleteCustomers(@RequestBody @Valid CustomersDeleteRequest request) {

        int count = customerService.delete(request.getCustomerIds());

        return BaseResponse.success(new CustomersDeleteResponse(count));
    }

    /**
     * 批量新增开放平台客户
     *
     * {@link CustomerService#batchSaveCustomerAll}
     */
    @Override
    public BaseResponse<CustomerBatchAddResponse> batchSaveCustomer(@RequestBody @Valid CustomerBatchAddRequest requestList) {
        CustomerBatchAddResponse response = new CustomerBatchAddResponse();
        response.setCustomerIds(customerService.batchSaveCustomerAll(requestList.getCustomerAddList()));
        return BaseResponse.success(response);
    }

    /**
     * 新增客户共通
     *
     * {@link CustomerService#saveCustomerAll}
     */
    @Override
    public BaseResponse<CustomerAddResponse> saveCustomer(@RequestBody @Valid CustomerAddRequest request) {
        CustomerAddResponse response = customerService.saveCustomerAll(request);
        return BaseResponse.success(response);
    }


    /**
     * Boss端修改会员
     * 修改会员表，修改会员详细信息
     *
     * {@link CustomerService#updateCustomerAll}
     */
    @Override
    public BaseResponse<CustomerModifyResponse> modifyCustomer(@RequestBody @Valid CustomerModifyRequest request) {
        CustomerDetailToEsVO customerDetailToEsVO = customerService.updateCustomerAll(request);

        return BaseResponse.success(CustomerModifyResponse.builder().customerDetailToEsVO(customerDetailToEsVO).build());
    }

    /**
     * 修改绑定手机号
     *
     * {@link CustomerService#updateCustomerAccount}
     */
    @Override
    public BaseResponse<CustomerAccountModifyResponse> modifyCustomerAccount(@RequestBody @Valid CustomerAccountModifyRequest request) {
        int count = customerService.updateCustomerAccount(request);

        return BaseResponse.success(new CustomerAccountModifyResponse(count));
    }

    /**
     * 修改已有的业务员
     *
     * {@link CustomerService#updateCustomerSalesMan}
     */
    @Override
    public BaseResponse modifyCustomerSalesMan(@RequestBody @Valid CustomerSalesManModifyRequest request) {
        customerService.updateCustomerSalesMan(request.getEmployeeId(), request.getAccountType());

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 更新会员为销售员
     *
     * @param request {@link CustomerToDistributorModifyRequest}
     * @return
     */
    @Override
    public BaseResponse modifyToDistributor(@RequestBody @Valid CustomerToDistributorModifyRequest request){
        customerService.updateCustomerToDistributor(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 0点执行登录次数清0操作
     * @return
     */
    @Override
    public BaseResponse modifyLoginErrorTime() {
        customerService.modifyLoginErrorTime();
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 用户更换头像
     * @param request
     * @return
     */
    @Override
    public BaseResponse editHeadImg(@RequestBody @Valid EditHeadImgRequest request) {
        customerService.editHeadImg(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改客户注销状态
     * @return
     */
    @Override
    public BaseResponse modifyLogOutStatusAndReason(CustomerLogoutStatusModifyRequest request) {
        customerService.modifyLogOutStatusAndReason(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改客户注销状态
     * @return
     */
    @Override
    public BaseResponse customerRefresh() {
        customerService.customerRefresh();
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyNewCustomerState(@RequestBody @Valid CustomerAccountModifyStateRequest request) {
        customerService.modifyNewCustomerState(request.getCustomerId(),request.getIsNew());
        return BaseResponse.SUCCESSFUL();
    }
}
