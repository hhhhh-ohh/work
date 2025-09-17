package com.wanmi.ares.marketing.flashsale.dao;

import com.wanmi.ares.request.marketing.*;
import com.wanmi.ares.response.FlashSaleGoods;
import com.wanmi.ares.response.FlashSaleOverview;
import com.wanmi.ares.response.MarketingInfoResp;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface FlashSaleStatisticsMapper {

    /**
     * 保存秒杀信息
     * @return
     */
    int insertFlashSaleOverViewByTrade(MarketingAnalysisJobRequest jobRequest);

    /**
     * 秒杀活动营销概况
     * @param query
     * @return
     */
    FlashSaleOverview queryFlashSaleOverView(MarketingQueryRequest query);

    /**
     * 秒杀活动数据趋势图 - 天
     * @param query
     * @return
     */
    List<FlashSaleOverview> queryFlashSaleByDay(MarketingQueryRequest query);

    /**
     * 秒杀活动数据趋势图 - 周
     * @param query
     * @return
     */
    List<FlashSaleOverview> queryFlashSaleByWeek(MarketingQueryRequest query);

    /**
     * 查询秒杀活动-选择活动组件
     * @param query
     * @return
     */
    List<MarketingInfoResp> queryFlashSaleList(SelectMarketingRequest query);

    /**
     * 查询秒杀活动总数-选择活动组件
     * @param query
     * @return
     */
    Long countFlashSale(SelectMarketingRequest query);

    /**
     * 秒杀活动效果
     * @param request
     * @return
     */
    List<FlashSaleGoods> queryGoodsForFlashSales(EffectPageRequest request);

    /**
     * 秒杀活动报表导出
     * @param request
     * @return
     */
    List<FlashSaleGoods> exportFlashSales(EffectPageRequest request);

    /**
     * 秒杀活动报表导出总数
     * @param request
     * @return
     */
    Long countExportFlashSales(EffectPageRequest request);

    /**
     * 查询区间内或所选活动中营销活动最早开始时间
     * @param request
     * @return
     */
    LocalDate queryEarliestActivityTime(MarketingQueryEarliestDateRequest request);
}
