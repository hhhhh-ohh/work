package com.wanmi.sbc.ares;

import com.wanmi.ares.provider.CustomerReportQueryServiceProvider;
import com.wanmi.ares.request.customer.CustomerOrderQueryRequest;
import com.wanmi.ares.view.customer.CustomerOrderPageView;
import com.wanmi.ares.view.customer.CustomerOrderView;
import com.wanmi.sbc.common.base.BaseQueryResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Tag(name = "CustomerReportController", description = "客户报表统计")
@RestController
@Validated
@RequestMapping("/customer_report")
@Slf4j
public class CustomerReportController {

    @Autowired
    private CustomerReportQueryServiceProvider customerReportQueryServiceProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Operation(summary = "客户订单统计")
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boos_customer_order_sign_word")
    public BaseResponse<BaseQueryResponse<CustomerOrderView>> queryCustomerOrder(@RequestBody CustomerOrderQueryRequest request) throws Exception {
        try {
            Platform platform = commonUtil.getOperator().getPlatform();
            if (platform == Platform.SUPPLIER || platform == Platform.STOREFRONT) {
                Long company = commonUtil.getCompanyInfoId();
                if (nonNull(company)) {
                    request.setCompanyId(company.toString());
                } else {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
                }
            } else {
                if (StringUtils.isEmpty(request.getCompanyId()) || "".equals(request.getCompanyId())) {
                    request.setCompanyId("0");
                }
            }
            //平台自营,查询内容同boss
            if (platform == Platform.SUPPLIER && BoolFlag.NO.equals(commonUtil.getCompanyType())){
                request.setCompanyId("0");
            }
            CustomerOrderPageView customerOrderPageView = customerReportQueryServiceProvider.queryCustomerOrders(request);
            //获取用户注销状态
            List<String> customerIds = customerOrderPageView.getCustomerOrderViewList()
                    .stream()
                    .map(CustomerOrderView::getCustomerId).filter(StringUtils::isNotBlank)
                    .collect(Collectors.toList());
            Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
            customerOrderPageView.getCustomerOrderViewList().forEach(v->{
                if (StringUtils.isNotBlank(v.getCustomerId())){
                    v.setLogOutStatus(map.get(v.getCustomerId()));
                }
            });
            BaseResponse response = BaseResponse.success(BaseQueryResponse
                    .<CustomerOrderView>builder()
                    .pageSize(request.getPageSize())
                    .total(customerOrderPageView.getTotal())
                    .pageNum(request.getPageNum())
                    .data(customerOrderPageView.getCustomerOrderViewList()).build());
            log.info(response.toString());
            return response;
        } catch (Exception e) {
            log.error("查询会员订货报表失败,", e);
            return BaseResponse.FAILED();
        }
    }

}
