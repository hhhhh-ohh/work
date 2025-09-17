package com.wanmi.ares.marketing.bargain.dao;

import com.wanmi.ares.request.marketing.EffectPageRequest;
import com.wanmi.ares.request.marketing.MarketingAnalysisJobRequest;
import com.wanmi.ares.request.marketing.MarketingQueryRequest;
import com.wanmi.ares.request.marketing.SelectMarketingRequest;
import com.wanmi.ares.response.GoodsBargainOverview;
import com.wanmi.ares.response.GoodsBargainReport;
import com.wanmi.ares.response.MarketingInfoResp;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: wur
 * @Date: 2022/8/23 11:33
 */
@Repository
public interface GoodsBargainStatisticsMapper {

    /**
     * 保存活动数据
     * @return
     */
    int insertBargainGoodsOverViewByTrade(MarketingAnalysisJobRequest jobRequest);

    GoodsBargainReport queryBargainGoodsOverView(MarketingQueryRequest queryRequest);

    /**
     * 分页查询活动列表
     * @param query
     * @return
     */
    List<MarketingInfoResp> queryBargainGoodsList(SelectMarketingRequest query);

    /**
     * 查询活动总数据
     * @param query
     * @return
     */
    Long countByPageTotal(SelectMarketingRequest query);

    /**
     * 活动数据趋势图 - 天
     * @param query
     * @return
     */
    List<GoodsBargainOverview> queryBargainByDay(MarketingQueryRequest query);

    /**
     * 活动数据趋势图 - 周
     * @param query
     * @return
     */
    List<GoodsBargainOverview> queryBargainByWeek(MarketingQueryRequest query);

    /**
     * 活动效果
     * @param request
     * @return
     */
    List<GoodsBargainReport> queryGoodsForBargainGoods(EffectPageRequest request);

    /**
     * 秒杀活动报表导出
     * @param request
     * @return
     */
    List<GoodsBargainReport> exportBargain(EffectPageRequest request);

    /**
     * 秒杀活动报表导出总数
     * @param request
     * @return
     */
    Long countExportBargain(EffectPageRequest request);
}
