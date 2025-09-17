package com.wanmi.ares.provider;

import com.wanmi.ares.request.paymember.PayMemberAreaQueryRequest;
import com.wanmi.ares.request.paymember.PayMemberGrowthRequest;
import com.wanmi.ares.request.paymember.PayMemberTrendRequest;
import com.wanmi.ares.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author xuyunpeng
 * @className PayMemberQueryProvider
 * @description
 * @date 2022/5/25 2:11 PM
 **/
@FeignClient(name = "${application.ares.name}", contextId="PayMemberQueryProvider")
public interface PayMemberQueryProvider {

    /**
     * 会员区域数据
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/paymember/area-view")
    PayMemberAreaViewResponse payMemberAreaView(@RequestBody @Valid PayMemberAreaQueryRequest request);

    /**
     * 付费会员概况
     * @param
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/paymember/overview")
    PayMemberOverViewResponse overView();

    /**
     * 付费会员新增报表数据
     * @param
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/paymember/growth/new")
    PayMemberGrowthNewResponse growthNewView(@RequestBody @Valid PayMemberGrowthRequest request);

    /**
     * 付费会员续费报表数据
     * @param
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/paymember/growth/renewal")
    PayMemberGrowthRenewalResponse growthRenewalView(@RequestBody @Valid PayMemberGrowthRequest request);

    /**
     * 付费会员新增趋势数据
     * @param
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/paymember/growth/new/trend")
    PayMemberGrowthNewTrendResponse growthNewTrendView(@RequestBody @Valid PayMemberTrendRequest request);

    /**
     * 付费会员续费趋势数据
     * @param
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/paymember/growth/renewal/trend")
    PayMemberGrowthRenewalTrendResponse growthRenewalTrendView(@RequestBody @Valid PayMemberTrendRequest request);
}
