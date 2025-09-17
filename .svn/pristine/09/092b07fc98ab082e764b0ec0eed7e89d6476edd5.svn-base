package com.wanmi.sbc.customer.provider.impl.loginregister;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteQueryProvider;
import com.wanmi.sbc.customer.api.request.loginregister.*;
import com.wanmi.sbc.customer.api.response.loginregister.*;
import com.wanmi.sbc.customer.bean.dto.ThirdLoginRelationDTO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetail;
import com.wanmi.sbc.customer.detail.service.CustomerDetailService;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.quicklogin.model.root.ThirdLoginRelation;
import com.wanmi.sbc.customer.service.CustomerSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: wanggang
 * @CreateDate: 2018/9/17 14:40
 * @Version: 1.0
 */
@Validated
@RestController
public class CustomerSiteQueryController implements CustomerSiteQueryProvider{

    @Autowired
    private CustomerSiteService customerSiteService;

    @Autowired
    private CustomerDetailService customerDetailService;

    /**
     * 会员登录
     *
     * @param customerLoginRequest
     * @return
     */

    @Override
    public BaseResponse<CustomerLoginResponse> login(@RequestBody @Valid CustomerLoginRequest customerLoginRequest) {
        Customer customer = customerSiteService.login(customerLoginRequest.getCustomerAccount(),customerLoginRequest.getPassword());
        if (Objects.isNull(customer)){
           return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.success(KsBeanUtil.convert(customer,CustomerLoginResponse.class));
    }


    @Override
    public BaseResponse updateLoginTime(@RequestBody @Valid CustomerUpdateLoginTimeRequest updateLoginTimeRequest) {
        customerSiteService.updateLoginTime(updateLoginTimeRequest.getCustomerId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 根据会员账号查询会员信息
     *
     * @param customerByAccountRequest
     * @return
     */

    @Override
    public BaseResponse<CustomerByAccountResponse> getCustomerByCustomerAccount(@RequestBody @Valid CustomerByAccountRequest customerByAccountRequest) {
        Customer customer = customerSiteService.getCustomerByAccount(customerByAccountRequest.getCustomerAccount());
        CustomerByAccountResponse customerByAccountResponse = CustomerByAccountResponse.builder().build();
        if (Objects.isNull(customer)){
            return BaseResponse.SUCCESSFUL();
        }
        KsBeanUtil.copyPropertiesThird(customer,customerByAccountResponse);
        CustomerDetail customerDetail = customerDetailService.findByCustomerId(customer.getCustomerId());
        CustomerDetailVO customerDetailVO = new CustomerDetailVO();
        KsBeanUtil.copyPropertiesThird(customerDetail, customerDetailVO);
        customerByAccountResponse.setCustomerDetail(customerDetailVO);
        return BaseResponse.success(customerByAccountResponse);
    }

    @Override
    public BaseResponse<CustomerByCustomerAccountResponse> getCustomerByCustomerAccountList(@RequestBody @Valid CustomerByAccountListRequest request) {
        CustomerByCustomerAccountResponse response = CustomerByCustomerAccountResponse.builder().build();
        List<Customer> customerByAccountList = customerSiteService.getCustomerByAccountList(request.getCustomerAccountList());
        List<CustomerVO> customerVOList = customerByAccountList.stream().map(customer -> {
            CustomerVO customerVO = new CustomerVO();
            KsBeanUtil.copyPropertiesThird(customer, customerVO);
            CustomerDetailVO customerDetailVO = new CustomerDetailVO();

            CustomerDetail customerDetail = customerDetailService.findByCustomerId(customer.getCustomerId());
            KsBeanUtil.copyPropertiesThird(customerDetail, customerDetailVO);
            customerVO.setCustomerDetail(customerDetailVO);
            return customerVO;
        }).collect(Collectors.toList());
        response.setCustomerVOList(customerVOList);
        return BaseResponse.success(response);
    }


    /**
     * 根据会员账号查询会员详情信息
     *
     * @param customerDetailByAccountRequest
     * @return
     */

    @Override
    public BaseResponse<CustomerDetailByAccountResponse> getCustomerDetailByCustomerAccount(@RequestBody @Valid CustomerDetailByAccountRequest customerDetailByAccountRequest) {
        CustomerDetail customerDetail = customerSiteService.getCustomerDetailByAccount(customerDetailByAccountRequest.getCustomerAccount());
        CustomerDetailByAccountResponse customerDetailByAccountResponse = CustomerDetailByAccountResponse.builder().build();
        if (Objects.isNull(customerDetail)){
            return BaseResponse.SUCCESSFUL();
        }
        KsBeanUtil.copyPropertiesThird(customerDetail,customerDetailByAccountResponse);
        return BaseResponse.success(customerDetailByAccountResponse);
    }


    /**
     * 第三方快捷登录并绑定账号信息
     *
     * @param customerLoginAndBindThirdAccountRequest
     * @return
     */

    @Override
    public BaseResponse<ThirdLoginAndBindResponse> loginAndBindThirdAccount(@RequestBody @Valid CustomerLoginAndBindThirdAccountRequest customerLoginAndBindThirdAccountRequest) {
        ThirdLoginRelationDTO thirdLoginRelationDTO = customerLoginAndBindThirdAccountRequest.getThirdLoginRelationDTO();
        ThirdLoginRelation thirdLoginRelation = new ThirdLoginRelation();
        KsBeanUtil.copyPropertiesThird(thirdLoginRelationDTO,thirdLoginRelation);
        ThirdLoginAndBindResponse customer = customerSiteService.thirdLoginAndBind(
                customerLoginAndBindThirdAccountRequest.getPhone(),
                thirdLoginRelation,
                customerLoginAndBindThirdAccountRequest.getShareUserId());
        if (Objects.isNull(customer)){
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.success(customer);
    }


    @Override
    public BaseResponse checkByAccount(@RequestBody @Valid CustomerCheckByAccountRequest request){
        customerSiteService.beforeRegister(request.getCustomerAccount());
        return BaseResponse.SUCCESSFUL();
    }
}
