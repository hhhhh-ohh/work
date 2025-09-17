package com.wanmi.sbc.distribute;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.performance.DistributionPerformanceQueryProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.performance.DistributionPerformanceByDayQueryRequest;
import com.wanmi.sbc.customer.api.request.distribution.performance.DistributionPerformanceByLast6MonthsQueryRequest;
import com.wanmi.sbc.customer.api.request.distribution.performance.DistributionPerformanceSummaryQueryRequest;
import com.wanmi.sbc.customer.api.request.distribution.performance.DistributionPerformanceYesterdayQueryRequest;
import com.wanmi.sbc.customer.api.response.distribution.performance.DistributionPerformanceByDayQueryResponse;
import com.wanmi.sbc.customer.api.response.distribution.performance.DistributionPerformanceByLast6MonthsQueryResponse;
import com.wanmi.sbc.customer.api.response.distribution.performance.DistributionPerformanceSummaryQueryResponse;
import com.wanmi.sbc.customer.api.response.distribution.performance.DistributionPerformanceYesterdayQueryResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionPerformanceSummaryVO;
import com.wanmi.sbc.util.CommonUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.Objects;

/**
 * 分销员业绩统计
 *
 * @author liutao
 * @date 2019/4/19 10:15 AM
 */
@Tag(name = "DistributionPerformanceController", description = "分销员业绩统计")
@RestController
@Validated
@RequestMapping("/distribution/performance")
public class DistributionPerformanceController {

    @Autowired
    private DistributionPerformanceQueryProvider distributionPerformanceQueryProvider;

    @Autowired
    private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 查询日销售业绩
     *
     * @param request
     * @return
     */
    @Operation(summary = "查询日销售业绩")
    @RequestMapping(value = "/day", method = RequestMethod.POST)
    public BaseResponse<DistributionPerformanceByDayQueryResponse> queryByDay(
            @RequestBody @Valid DistributionPerformanceByDayQueryRequest request) {
        //客户id
        String customerId = commonUtil.getOperatorId();
        //分销员id
        String distributionId =
                distributionCustomerQueryProvider.getByCustomerId(new DistributionCustomerByCustomerIdRequest(customerId))
                        .getContext().getDistributionCustomerVO().getDistributionId();
        if(!Objects.equals(request.getDistributionId(), distributionId)) {
            return BaseResponse.error("非法越权操作");
        }
        return distributionPerformanceQueryProvider.queryByDay(request);
    }

    /**
     * 查询昨天的销售业绩
     *
     * @param request
     * @return
     */
    @Operation(summary = "查询昨天的销售业绩")
    @RequestMapping(value = "/yesterday", method = RequestMethod.POST)
    public BaseResponse<DistributionPerformanceYesterdayQueryResponse> queryYesterday(
            @RequestBody @Valid DistributionPerformanceYesterdayQueryRequest request) {
        //客户id
        String customerId = commonUtil.getOperatorId();
        //分销员id
        String distributionId =
                distributionCustomerQueryProvider.getByCustomerId(new DistributionCustomerByCustomerIdRequest(customerId))
                        .getContext().getDistributionCustomerVO().getDistributionId();
        if(!Objects.equals(request.getDistributionId(), distributionId)) {
            return BaseResponse.error("非法越权操作");
        }
        return distributionPerformanceQueryProvider.queryYesterday(request);
    }

    /**
     * 查询月销售业绩
     *
     * @param request
     * @return
     */
    @Operation(summary = "查询月销售业绩")
    @RequestMapping(value = "/month", method = RequestMethod.POST)
    public BaseResponse<DistributionPerformanceByLast6MonthsQueryResponse> queryByLast6Months(
            @RequestBody @Valid DistributionPerformanceByLast6MonthsQueryRequest request) {
        //客户id
        String customerId = commonUtil.getOperatorId();
        //分销员id
        String distributionId =
                distributionCustomerQueryProvider.getByCustomerId(new DistributionCustomerByCustomerIdRequest(customerId))
                        .getContext().getDistributionCustomerVO().getDistributionId();
        if(!Objects.equals(request.getDistributionId(), distributionId)) {
            return BaseResponse.error("非法越权操作");
        }
        return distributionPerformanceQueryProvider.queryByLast6Months(request);
    }

    /**
     * 查询当月截止昨天的分销业绩
     * @return
     */
    @Operation(summary = "查询当前月1号至昨天的业绩汇总")
    @RequestMapping(value = "/summary/month", method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse<DistributionPerformanceSummaryQueryResponse> summaryPerformanceCurrentMonth() {
        //客户id
        String customerId = commonUtil.getOperatorId();
        //分销员id
        String distributionId =
                distributionCustomerQueryProvider.getByCustomerId(new DistributionCustomerByCustomerIdRequest(customerId))
                        .getContext().getDistributionCustomerVO().getDistributionId();

        DistributionPerformanceSummaryQueryRequest request = DistributionPerformanceSummaryQueryRequest.builder()
                .distributionId(Collections.singletonList(distributionId))
                .startDate(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()))
                .endDate(LocalDate.now().minusDays(1L))
                .build();
        if(request.getStartDate().isAfter(request.getEndDate()) || request.getStartDate().isEqual(request.getEndDate())){
            DistributionPerformanceSummaryQueryResponse response = new DistributionPerformanceSummaryQueryResponse();
            DistributionPerformanceSummaryVO distributionPerformanceSummaryVO = new DistributionPerformanceSummaryVO();
            distributionPerformanceSummaryVO.setCustomerId(customerId);
            distributionPerformanceSummaryVO.setDistributionId(distributionId);
            distributionPerformanceSummaryVO.setCommission(BigDecimal.ZERO);
            distributionPerformanceSummaryVO.setSaleAmount(BigDecimal.ZERO);
            response.setDataList(Collections.singletonList(distributionPerformanceSummaryVO));
            return BaseResponse.success(response);
        }
        return distributionPerformanceQueryProvider.queryPerformanceSummary(request);
    }

}
