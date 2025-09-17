package com.wanmi.ares.provider.impl;

import com.wanmi.ares.provider.PayMemberQueryProvider;
import com.wanmi.ares.report.paymember.model.root.PayMemberGrowReport;
import com.wanmi.ares.report.paymember.service.PayMemberQueryService;
import com.wanmi.ares.request.paymember.PayMemberAreaQueryRequest;
import com.wanmi.ares.request.paymember.PayMemberGrowthRequest;
import com.wanmi.ares.request.paymember.PayMemberQueryRequest;
import com.wanmi.ares.request.paymember.PayMemberTrendRequest;
import com.wanmi.ares.response.*;
import com.wanmi.ares.view.paymember.PayMemberGrowthNewTrendView;
import com.wanmi.ares.view.paymember.PayMemberGrowthRenewalTrendView;
import com.wanmi.sbc.common.util.KsBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className PayMemberQueryController
 * @description
 * @date 2022/5/25 2:31 PM
 **/
@RestController
public class PayMemberQueryController implements PayMemberQueryProvider {

    @Autowired
    private PayMemberQueryService payMemberQueryService;

    @Override
    public PayMemberAreaViewResponse payMemberAreaView(@RequestBody @Valid PayMemberAreaQueryRequest request) {
        PayMemberQueryRequest queryRequest = KsBeanUtil.convert(request, PayMemberQueryRequest.class);
        return payMemberQueryService.queryAreaView(queryRequest);
    }

    @Override
    public PayMemberOverViewResponse overView() {
        return payMemberQueryService.overView();
    }

    @Override
    public PayMemberGrowthNewResponse growthNewView(@RequestBody @Valid PayMemberGrowthRequest request) {
        return payMemberQueryService.queryGrowthNewList(request);
    }

    @Override
    public PayMemberGrowthRenewalResponse growthRenewalView(@RequestBody @Valid PayMemberGrowthRequest request) {
        return payMemberQueryService.queryGrowthRenewalList(request);
    }

    @Override
    public PayMemberGrowthNewTrendResponse growthNewTrendView(@RequestBody @Valid PayMemberTrendRequest request) {
        List<PayMemberGrowReport> reports = payMemberQueryService.queryGrowthTrend(request);

        List<PayMemberGrowthNewTrendView> viewList = reports.stream().map(r -> {
            PayMemberGrowthNewTrendView trendView = com.wanmi.ares.utils.KsBeanUtil.convert(r, PayMemberGrowthNewTrendView.class);
            trendView.setXValue(r.getBaseDate());
            return trendView;
        }).collect(Collectors.toList());
        return PayMemberGrowthNewTrendResponse.builder().viewList(viewList).build();
    }

    @Override
    public PayMemberGrowthRenewalTrendResponse growthRenewalTrendView(@RequestBody @Valid PayMemberTrendRequest request) {
        List<PayMemberGrowReport> reports = payMemberQueryService.queryGrowthTrend(request);

        List<PayMemberGrowthRenewalTrendView> viewList = reports.stream().map(r -> {
            PayMemberGrowthRenewalTrendView trendView = com.wanmi.ares.utils.KsBeanUtil.convert(r, PayMemberGrowthRenewalTrendView.class);
            trendView.setXValue(r.getBaseDate());
            return trendView;
        }).collect(Collectors.toList());
        return PayMemberGrowthRenewalTrendResponse.builder().viewList(viewList).build();
    }
}
