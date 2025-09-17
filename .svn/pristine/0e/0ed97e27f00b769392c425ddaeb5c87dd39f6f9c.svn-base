package com.wanmi.sbc.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.request.detail.*;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import io.jsonwebtoken.Claims;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 会员详细信息
 * Created by CHENLI on 2017/4/20.
 */
@Tag(name = "CustomerDetailController", description = "会员详细信息")
@RestController
@Validated
@RequestMapping("/customer")
public class CustomerDetailController {

    @Autowired
    private CustomerDetailProvider customerDetailProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    /**
     * 通过会员ID查询单条会员详细信息
     *
     * @param customerId
     * @return
     */
    @Operation(summary = "通过会员ID查询单条会员详细信息")
    @Parameter(name = "customerId", description = "会员Id", required = true)
    @RequestMapping(value = "/detail/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<CustomerDetailVO> findById(@PathVariable String customerId) {
        CustomerDetailVO customerDetail = customerDetailQueryProvider.getCustomerDetailWithNotDeleteByCustomerId(
                CustomerDetailWithNotDeleteByCustomerIdRequest.builder().customerId(customerId).build()
        ).getContext();
        return ResponseEntity.ok(customerDetail);
    }

    /**
     * 通过会员详情ID查询会员详情
     *
     * @param customerDetailId
     * @return
     */
    @Operation(summary = "通过会员详情ID查询会员详情")
    @Parameter(name = "customerDetailId", description = "会员详情Id",
            required = true)
    @RequestMapping(value = "/detailInfo/{customerDetailId}", method = RequestMethod.GET)
    public ResponseEntity<CustomerDetailVO> findOne(@PathVariable String customerDetailId) {
        CustomerDetailVO customerDetail = customerDetailQueryProvider.getCustomerDetailById(
                CustomerDetailByIdRequest.builder().customerDetailId(customerDetailId).build()
        ).getContext();
        return ResponseEntity.ok(customerDetail);
    }

    /**
     * 保存会员详细信息
     *
     * @param customerDetailEditRequest
     * @return
     */
    @Operation(summary = "保存会员详细信息")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> add(@RequestBody CustomerDetailEditRequest customerDetailEditRequest,
                                            HttpServletRequest request) {
        String employeeId = ((Claims) request.getAttribute("claims")).get("employeeId").toString();
        CustomerDetailAddRequest addRequest = new CustomerDetailAddRequest();
        addRequest.setCreatePerson(employeeId);
        KsBeanUtil.copyPropertiesThird(customerDetailEditRequest, addRequest);
        return ResponseEntity.ok(customerDetailProvider.addCustomerDetail(addRequest));
    }

    /**
     * 批量删除会员详情
     *
     * @param customerIds
     * @return
     */
    @Operation(summary = "批量删除会员详情")
    @Parameter(name = "customerIds", description = "会员Id集合",
            required = true)
    @RequestMapping(value = "/detail/{customerIds}", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> delete(@PathVariable List<String> customerIds) {
        if (CollectionUtils.isEmpty(customerIds)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomerDetailDeleteRequest request = CustomerDetailDeleteRequest.builder().customerIds(customerIds).build();
        return ResponseEntity.ok(customerDetailProvider.deleteCustomerDetailByCustomerIds(request));
    }

}
