package com.wanmi.ares.marketing.appointment.dao;

import com.wanmi.ares.marketing.appointment.model.entity.CountAppointmentForDay;
import com.wanmi.ares.marketing.appointment.model.entity.CountAppointmentForWeek;
import com.wanmi.ares.marketing.appointment.model.entity.FullAppointmentCountMap;
import com.wanmi.ares.request.marketing.*;
import com.wanmi.ares.response.AppointmentAnalysisForGoods;
import com.wanmi.ares.response.AppointmentAnalysisOverview;
import com.wanmi.ares.response.MarketingInfoResp;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentStatisticsMapper {

    public List<MarketingInfoResp> getAppointmentSaleList(SelectMarketingRequest request);

    /**
     * 查询当天的预约订单数量
     * @return
     */
    long countTodayOrderForAppointment();

    long saveAppointmentTradeDetail(MarketingAnalysisJobRequest jobRequest);

    AppointmentAnalysisOverview analysisOverview(MarketingQueryRequest queryParams);

    List<AppointmentAnalysisOverview> analysisOverviewForDayByBoss(MarketingQueryRequest queryParams);

    List<AppointmentAnalysisOverview> analysisOverviewForDay(MarketingQueryRequest queryParams);

    List<AppointmentAnalysisOverview> analysisOverviewForWeekByBoss(MarketingQueryRequest queryParams);

    List<AppointmentAnalysisOverview> analysisOverviewForWeek(MarketingQueryRequest queryParams);

    Long countAppointment(MarketingQueryRequest queryParams);

    List<CountAppointmentForDay> countAppointmentForDay(MarketingQueryRequest queryParams);

    List<CountAppointmentForWeek> countAppointmentForWeek(MarketingQueryRequest queryParams);

    Long countByPageTotal(SelectMarketingRequest request);

    List<AppointmentAnalysisForGoods> analysisForGoods(EffectPageRequest queryParams);

    Long countForAppointmentGoods(EffectPageRequest request);

    List<AppointmentAnalysisForGoods> analysisForReport(EffectPageRequest queryParams);

    List<FullAppointmentCountMap> fullAppointmentCount(List<Long> saleIdList);

    /**
     * 查询区间内或所选活动中营销活动最早开始时间
     * @param request
     * @return
     */
    LocalDate queryEarliestActivityTime(MarketingQueryEarliestDateRequest request);
}
