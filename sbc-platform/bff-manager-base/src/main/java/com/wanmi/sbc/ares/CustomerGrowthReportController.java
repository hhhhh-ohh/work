package com.wanmi.sbc.ares;

import com.wanmi.ares.provider.CustomerGrowthReportServiceProvider;
import com.wanmi.ares.request.customer.CustomerGrowthReportRequest;
import com.wanmi.ares.request.customer.CustomerTrendQueryRequest;
import com.wanmi.ares.view.customer.CustomerGrowthPageView;
import com.wanmi.ares.view.customer.CustomerGrowthReportView;
import com.wanmi.ares.view.customer.CustomerGrowthTrendView;
import com.wanmi.sbc.common.base.BaseQueryResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.configure.ThriftClientConfig;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static java.util.Objects.nonNull;

@Tag(name = "CustomerGrowthReportController", description = "客户增长统计")
@RestController
@Validated
@RequestMapping("/customer_grow")
@Slf4j
@EnableConfigurationProperties(ThriftClientConfig.class)
public class CustomerGrowthReportController {

    @Autowired
    private CustomerGrowthReportServiceProvider customerGrowthReportServiceProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "客户增长列表统计")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<BaseQueryResponse<CustomerGrowthReportView>> queryCustomerGrowthList(
            @RequestBody CustomerGrowthReportRequest reportRequest) {
        try {
            Platform platform = getPlatform();
            if (platform == Platform.SUPPLIER || platform == Platform.STOREFRONT) {
                Long company = commonUtil.getCompanyInfoId();
                if (nonNull(company)) {
                    reportRequest.setCompanyId(company.toString());
                } else {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
                }
            }
            //平台自营,查询内容同boss
            if (platform == Platform.SUPPLIER && BoolFlag.NO.equals(commonUtil.getCompanyType())){
                reportRequest.setCompanyId(null);
            }

            CustomerGrowthPageView customerGrowthPageView = customerGrowthReportServiceProvider.queryCustomerGrouthList(reportRequest);
            log.info("返回结果：{}", customerGrowthPageView);
            return BaseResponse.success(BaseQueryResponse.<CustomerGrowthReportView>builder()
                    .total(customerGrowthPageView.getTotal())
                    .pageSize(reportRequest.getPageSize())
                    .data(customerGrowthPageView.getGrouthList())
                    .pageNum(reportRequest.getPageNum())
                    .build());
        } catch (Exception e) {
            log.error("用户增长请求失败,", e);
            return BaseResponse.FAILED();

        }
    }

    @Operation(summary = "客户增长趋势统计")
    @RequestMapping(value = "/trend", method = RequestMethod.POST)
    public BaseResponse<List<CustomerGrowthTrendView>> queryCustomerTrendList(
            @RequestBody CustomerTrendQueryRequest request) {
        try {
            Platform platform = getPlatform();
            if (platform == Platform.SUPPLIER || platform == Platform.STOREFRONT) {
                Long company = commonUtil.getCompanyInfoId();
                if (nonNull(company)) {
                    request.setCompanyInfoId(company.toString());
                } else {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
                }
            }
            //平台自营,查询内容同boss
            if (platform == Platform.SUPPLIER && BoolFlag.NO.equals(commonUtil.getCompanyType())){
                request.setCompanyInfoId(null);
            }
            List<CustomerGrowthTrendView> list = customerGrowthReportServiceProvider.queryCustomerTrendList(request);
            return BaseResponse.success(list);
        } catch (Exception e) {
            log.error("用户增长请求失败,", e);
            return BaseResponse.FAILED();
        }
    }

    private Platform getPlatform(){
        return commonUtil.getOperator().getPlatform();
    }
}