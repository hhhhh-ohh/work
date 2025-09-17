package com.wanmi.sbc.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressProvider;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.address.*;
import com.wanmi.sbc.customer.api.request.customer.CustomerDelFlagGetRequest;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressAddResponse;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressListResponse;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressModifyResponse;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDeliveryAddressVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import io.jsonwebtoken.Claims;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 客户地址
 * Created by CHENLI on 2017/4/20.
 */
@Tag(name = "CustomerDeliveryAddressController", description = "客户地址服务API")
@RestController
@Validated
@RequestMapping("/customer")
public class CustomerDeliveryAddressController {

    @Autowired
    private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    @Autowired
    private CustomerDeliveryAddressProvider customerDeliveryAddressProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    /**
     * 查询该客户的所有收货地址
     * @param customerId
     * @return
     */
    @Operation(summary = "查询该客户的所有收货地址")
    @Parameter(name = "customerId", description = "会员Id", required = true)
    @RequestMapping(value = "/addressAllList/{customerId}", method = RequestMethod.GET)
    @ReturnSensitiveWords(functionName = "f_supplier_find_address_list_word_sign")
    public BaseResponse<List<CustomerDeliveryAddressVO>> allAddressList(@PathVariable String customerId){
        CustomerDeliveryAddressListRequest request = new CustomerDeliveryAddressListRequest();
        request.setCustomerId(customerId);
        List<CustomerDeliveryAddressVO> addressList = customerDeliveryAddressQueryProvider.listByCustomerId(request).getContext()
                .getCustomerDeliveryAddressVOList();
        return BaseResponse.success(addressList);
    }

    /**
     * 查询该客户的所有收货地址
     * @param customerId
     * @return
     */
    @Operation(summary = "查询该客户的所有收货地址")
    @Parameter(name = "customerId", description = "会员Id", required = true)
    @RequestMapping(value = "/addressList/{customerId}", method = RequestMethod.GET)
    @ReturnSensitiveWords(functionName = "f_supplier_find_address_list_word_sign")
    public BaseResponse<List<CustomerDeliveryAddressVO>> findAddressList(@PathVariable String customerId){
        CustomerDeliveryAddressListRequest request = new CustomerDeliveryAddressListRequest();
        request.setCustomerId(customerId);
        BaseResponse<CustomerDeliveryAddressListResponse> customerDeliveryAddressListResponseBaseResponse = customerDeliveryAddressQueryProvider.listByCustomerId(request);
        CustomerDeliveryAddressListResponse customerDeliveryAddressListResponse = customerDeliveryAddressListResponseBaseResponse.getContext();
        if (Objects.nonNull(customerDeliveryAddressListResponse)){
                List<CustomerDeliveryAddressVO> customerDeliveryAddressVOList = customerDeliveryAddressListResponse.getCustomerDeliveryAddressVOList();
            if(CollectionUtils.isNotEmpty(customerDeliveryAddressVOList)) {
                // 过滤掉需要用户完善的收货地址
                List<CustomerDeliveryAddressVO> addressVOList =
                        customerDeliveryAddressVOList.stream().filter(v -> !Boolean.TRUE.equals(v.getNeedComplete())).collect(Collectors.toList());
                return BaseResponse.success(CollectionUtils.isNotEmpty(addressVOList) ? addressVOList : Collections.emptyList());
            }
        }
        return BaseResponse.success(Collections.emptyList());
    }

    /**
     * 查询客户默认收货地址
     * @param queryRequest
     * @return
     */
    @Operation(summary = "查询客户默认收货地址")
    @RequestMapping(value = "/addressinfo", method = RequestMethod.POST)
    public BaseResponse<CustomerDeliveryAddressResponse> findDefaultAddress(@RequestBody CustomerDeliveryAddressRequest queryRequest){
        BaseResponse<CustomerDeliveryAddressResponse> customerDeliveryAddressResponseBaseResponse = customerDeliveryAddressQueryProvider.getDefaultOrAnyOneByCustomerId(queryRequest);
        CustomerDeliveryAddressResponse customerDeliveryAddressResponse = customerDeliveryAddressResponseBaseResponse.getContext();
        if (Objects.nonNull(customerDeliveryAddressResponse)){
            return BaseResponse.success(customerDeliveryAddressResponse);
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 保存客户收货地址
     * @param editRequest
     * @return
     */
    @Operation(summary = "保存客户收货地址")
    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> saveAddress(@Valid @RequestBody CustomerDeliveryAddressAddRequest editRequest, HttpServletRequest request){
        String customerId = editRequest.getCustomerId();
        CustomerVO customerVO = customerQueryProvider.getCustomerByCustomerId(CustomerDelFlagGetRequest.builder()
                .customerId(customerId)
                .build())
                .getContext();
        if (Objects.isNull(customerVO)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //H5一个客户最多可以添加20条地址
        CustomerDeliveryAddressByCustomerIdRequest customerDeliveryAddressByCustomerIdRequest = new CustomerDeliveryAddressByCustomerIdRequest();
        customerDeliveryAddressByCustomerIdRequest.setCustomerId(customerId);

        if (customerDeliveryAddressQueryProvider.countByCustomerId(customerDeliveryAddressByCustomerIdRequest)
                .getContext().getResult() >= 20){
            return ResponseEntity.ok(BaseResponse.error("最多可以添加20条收货地址"));
        }
        String employeeId = ((Claims) request.getAttribute("claims")).get("employeeId").toString();
        editRequest.setEmployeeId(employeeId);
        BaseResponse<CustomerDeliveryAddressAddResponse>  customerDeliveryAddressAddResponseBaseResponse = customerDeliveryAddressProvider.add(editRequest);
        CustomerDeliveryAddressAddResponse response = customerDeliveryAddressAddResponseBaseResponse.getContext();

        if(Objects.nonNull(response)){
            return ResponseEntity.ok(BaseResponse.success(response));
        }else{
            return ResponseEntity.ok(BaseResponse.FAILED());
        }
    }

    /**
     * 修改客户收货地址
     * @param editRequest
     * @return
     */
    @Operation(summary = "修改客户收货地址")
    @RequestMapping(value = "/address", method = RequestMethod.PUT)
    public ResponseEntity<BaseResponse> updateAddress(@Valid @RequestBody CustomerDeliveryAddressModifyRequest editRequest, HttpServletRequest request){
        String employeeId = ((Claims) request.getAttribute("claims")).get("employeeId").toString();
        editRequest.setEmployeeId(employeeId);
        BaseResponse<CustomerDeliveryAddressModifyResponse> customerDeliveryAddressModifyResponseBaseResponse = customerDeliveryAddressProvider.modify(editRequest);
        CustomerDeliveryAddressModifyResponse response = customerDeliveryAddressModifyResponseBaseResponse.getContext();
        if(Objects.nonNull(response)){
            return ResponseEntity.ok(BaseResponse.success(response));
        }else{
            return ResponseEntity.ok(BaseResponse.FAILED());
        }
    }

    /**
     * 删除客户收货地址
     * @param addressId
     * @return
     */
    @Operation(summary = "删除客户收货地址")
    @Parameter(name = "addressId", description = "收货地址Id", required = true)
    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.DELETE)
    public ResponseEntity<BaseResponse> deleteAddress(@PathVariable String addressId){
        if(StringUtils.isBlank(addressId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomerDeliveryAddressDeleteRequest customerDeliveryAddressDeleteRequest = new CustomerDeliveryAddressDeleteRequest();
        customerDeliveryAddressDeleteRequest.setAddressId(addressId);
        customerDeliveryAddressProvider.deleteById(customerDeliveryAddressDeleteRequest);
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }


    /**
     * 设置客户收货地址为默认
     * @param queryRequest
     * @return
     */
    @Operation(summary = "设置客户收货地址为默认")
    @RequestMapping(value = "/defaultAddress", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> setDefaultAddress(@RequestBody CustomerDeliveryAddressModifyDefaultRequest queryRequest){
        if(queryRequest.getDeliveryAddressId() == null && queryRequest.getCustomerId()==null){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        customerDeliveryAddressProvider.modifyDefaultByIdAndCustomerId(queryRequest);
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

}
