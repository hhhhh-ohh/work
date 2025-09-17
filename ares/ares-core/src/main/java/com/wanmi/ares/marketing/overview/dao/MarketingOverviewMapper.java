package com.wanmi.ares.marketing.overview.dao;

import com.wanmi.ares.request.marketing.MarketingOverviewRequest;
import com.wanmi.ares.response.MarketingOverview;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketingOverviewMapper {
    void insertOfDay();

    void delOfSevenDay();

    void insertOfSevenDay();

    void delOfThirtyDay();

    void insertOfThirtyDay();

    void insertOfMonth();

    // 昨天
    MarketingOverview findByDay(MarketingOverviewRequest request);

    // 最近7天
    MarketingOverview findBySeven(MarketingOverviewRequest request);

    // 最近30天
    MarketingOverview findByThirty(MarketingOverviewRequest request);

    // 自然月
    MarketingOverview findByMonth(MarketingOverviewRequest request);

    // 支付金额趋势图
    List<MarketingOverview> findMoneyTrendChart(MarketingOverviewRequest request);

    // 按天数据趋势图
    List<MarketingOverview> findTrendChart(MarketingOverviewRequest request);

    // 按周数据趋势图
    List<MarketingOverview> findTrendChartByWeek(MarketingOverviewRequest request);
}
