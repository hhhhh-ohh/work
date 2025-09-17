package com.wanmi.sbc.logout;

import com.wanmi.sbc.account.api.provider.credit.CustomerApplyRecordProvider;
import com.wanmi.sbc.account.api.request.credit.CreditApplyQueryRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.customer.CustomerProvider;
import com.wanmi.sbc.customer.api.provider.mqconsumer.CustomerMqConsumerProvider;
import com.wanmi.sbc.customer.api.request.mqconsumer.CustomerMqConsumerRequest;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.provider.customer.EsDistributionCustomerProvider;
import com.wanmi.sbc.elastic.api.provider.customerInvoice.EsCustomerInvoiceProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerInitRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoicePageRequest;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeReturnByIdRequest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;

/**
 * 注销用户刷新
 * Created by xufeng on 2022/4/1.
 */
@RestController("LogoutController")
@Validated
@RequestMapping("/logout")
@Tag(name =  "注销用户刷新API", description =  "LoginController")
@Slf4j
public class LogoutController {

    @Autowired
    private CustomerProvider customerProvider;

    @Autowired
    private CustomerMqConsumerProvider customerMqConsumerProvider;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    @Autowired
    private EsDistributionCustomerProvider esDistributionCustomerProvider;

    @Autowired
    private CouponCodeProvider couponCodeProvider;

    @Autowired
    private CustomerApplyRecordProvider customerApplyRecordProvider;

    @Autowired
    private EsCustomerInvoiceProvider esCustomerInvoiceProvider;

    /**
     * 注销用户刷新
     *
     * @return BaseResponse
     */
    @Operation(summary = "注销用户刷新接口，注销用户刷新")
    @GetMapping(value = "/customerRefresh")
    public BaseResponse customerRefresh() {
        return customerProvider.customerRefresh();
    }

    /**
     * 根据用户刷新注销状态-自动化测试用
     *
     * @return BaseResponse
     */
    @Operation(summary = "根据用户刷新注销状态-自动化测试用，根据用户刷新注销状态-自动化测试用")
    @RequestMapping(value = "/logoff", method = RequestMethod.POST)
    public BaseResponse logoff(@RequestBody LogoffRequest request) {
        log.info("用户注销mq执行开始...");
        for (String json:request.getCustomerIds()){
            CustomerMqConsumerRequest customerMqConsumerRequest = new CustomerMqConsumerRequest();
            customerMqConsumerRequest.setMqContentJson(json);
            boolean loggingOffFlag = (boolean) customerMqConsumerProvider.customerLogout(customerMqConsumerRequest).getContext();
            log.info("用户注销mq,loggingOffFlag:{}", loggingOffFlag);
            if (loggingOffFlag){
                // 优惠券过期时间更新
                couponCodeProvider.modifyByCustomerId(CouponCodeReturnByIdRequest.builder().customerId(json).build());
                // 授信待审核驳回
                customerApplyRecordProvider.modifyByCustomerId(CreditApplyQueryRequest.builder().customerId(json).build());
                //同步ES
                esCustomerDetailProvider.init(EsCustomerDetailInitRequest.builder()
                        .idList(Collections.singletonList(json)).build());
                esDistributionCustomerProvider.init(EsDistributionCustomerInitRequest.builder().customerId(json).build());
                EsCustomerInvoicePageRequest esCustomerInvoicePageRequest = new EsCustomerInvoicePageRequest();
                esCustomerInvoicePageRequest.setCustomerIds(Collections.singletonList(json));
                esCustomerInvoiceProvider.init(esCustomerInvoicePageRequest);
            }
        }
        log.info("用户注销mq执行结束...");
        return BaseResponse.SUCCESSFUL();
    }

}
