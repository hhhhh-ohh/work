package com.wanmi.ares.marketing.bookingsale.dao;

import com.wanmi.ares.request.marketing.*;
import com.wanmi.ares.response.DepositBookingReport;
import com.wanmi.ares.response.FullMoneyBookingReport;
import com.wanmi.ares.response.MarketingInfoResp;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingSaleStatisticsMapper {

    int insertFullMoneyBooking(MarketingAnalysisJobRequest jobRequest);

    FullMoneyBookingReport fullBookingByOverview(MarketingQueryRequest query);

    List<FullMoneyBookingReport> fullBookingByDay(MarketingQueryRequest query);

    List<FullMoneyBookingReport> fullBookingByWeek(MarketingQueryRequest query);

    List<FullMoneyBookingReport> fullBookingByList(EffectPageRequest query);

    // 在订金和尾款不在同一天内支付时可以正常统计
    int insertDepositBooking(MarketingAnalysisJobRequest jobRequest);

    // 弥补上条统计缺陷，订金和尾款在一天支付完成的情况
    int insertDepositBookingOfToday(MarketingAnalysisJobRequest jobRequest);

    DepositBookingReport depositBookingByOverview(MarketingQueryRequest query);

    List<DepositBookingReport> depositBookingByDay(MarketingQueryRequest query);

    List<DepositBookingReport> depositBookingByWeek(MarketingQueryRequest query);

    List<MarketingInfoResp> findMarketing(SelectMarketingRequest request);

    Long countByPageTotal(SelectMarketingRequest request);

    List<DepositBookingReport> depositBookingByList(EffectPageRequest request);

    /**
     * 查询区间内或所选活动中营销活动最早开始时间
     * @param request
     * @return
     */
    LocalDate queryEarliestActivityTime(MarketingQueryEarliestDateRequest request);

}
