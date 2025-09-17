package com.wanmi.sbc.ares;

import com.wanmi.ares.provider.PayMemberQueryProvider;
import com.wanmi.ares.request.paymember.PayMemberAreaQueryRequest;
import com.wanmi.ares.request.paymember.PayMemberGrowthRequest;
import com.wanmi.ares.request.paymember.PayMemberTrendRequest;
import com.wanmi.ares.response.*;
import com.wanmi.sbc.common.base.BaseResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * @author xuyunpeng
 * @className PayMemberReportController
 * @description
 * @date 2022/5/25 2:43 PM
 **/
@Tag(name = "PayMemberReportController", description = "付费会员数据报表 Api")
@RequestMapping("/paymember/report")
@RestController
@Validated
@Slf4j
public class PayMemberReportController {

    @Autowired
    private PayMemberQueryProvider payMemberQueryProvider;

    @Operation(summary = "付费会员区域统计")
    @PostMapping(value = "/area")
    public BaseResponse<PayMemberAreaViewResponse> areaView(@RequestBody @Valid PayMemberAreaQueryRequest request) {
        PayMemberAreaViewResponse response = payMemberQueryProvider.payMemberAreaView(request);
        return BaseResponse.success(response);
    }


    @Operation(summary = "付费会员开通报表数据")
    @PostMapping(value = "/growth/new")
    public BaseResponse<PayMemberGrowthNewResponse> growthView(@RequestBody @Valid PayMemberGrowthRequest request) {
        PayMemberGrowthNewResponse response = payMemberQueryProvider.growthNewView(request);
        return BaseResponse.success(response);
    }

    @Operation(summary = "付费会员续费报表数据")
    @PostMapping(value = "/growth/renewal")
    public BaseResponse<PayMemberGrowthRenewalResponse> growthRenewalView(@RequestBody @Valid PayMemberGrowthRequest request) {
        PayMemberGrowthRenewalResponse response = payMemberQueryProvider.growthRenewalView(request);
        return BaseResponse.success(response);
    }

    @Operation(summary = "付费会员开通趋势数据")
    @PostMapping(value = "/growth/new/trend")
    public BaseResponse<PayMemberGrowthNewTrendResponse> growthNewTrendView(@RequestBody @Valid PayMemberTrendRequest request) {
        PayMemberGrowthNewTrendResponse response = payMemberQueryProvider.growthNewTrendView(request);
        return BaseResponse.success(response);
    }

    @Operation(summary = "付费会员续费趋势数据")
    @PostMapping(value = "/growth/renewal/trend")
    public BaseResponse<PayMemberGrowthRenewalTrendResponse> growthRenewalTrendView(@RequestBody @Valid PayMemberTrendRequest request) {
        PayMemberGrowthRenewalTrendResponse response = payMemberQueryProvider.growthRenewalTrendView(request);
        return BaseResponse.success(response);
    }
}
