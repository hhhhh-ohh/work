package com.wanmi.ares.marketing.groupon.dao;

import com.wanmi.ares.request.marketing.*;
import com.wanmi.ares.response.GrouponOverview;
import com.wanmi.ares.response.MarketingInfoResp;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author：zhangwenchang
 * @Date：2021/1/13 13:59
 * @Description：
 */
@Repository
public interface GrouponStatisticsMapper {

    long insertByTrade(MarketingAnalysisJobRequest jobRequest);

     /**
      * @param query 条件
      * @return 概况数据
      */
    GrouponOverview grouponByOverview(MarketingQueryRequest query);

    /**
     *
     * @param query
     * @return 分享数量概况
     */
    GrouponOverview grouponShareCountByOverview(MarketingQueryRequest query);

     /**
      * @param query 条件
      * @return 趋势数据
      */
    List<GrouponOverview> grouponByDay(MarketingQueryRequest query);

    /**
     *
     * @param query
     * @return
     */
    List<GrouponOverview> grouponShareCountByDay(MarketingQueryRequest query);

    /**
     *
     * @param query
     * @return
     */
    List<GrouponOverview> grouponByWeek(MarketingQueryRequest query);

    /**
     *
     * @param query
     * @return
     */
    List<GrouponOverview> grouponShareCountByWeek(MarketingQueryRequest query);

    /**
     * 查询活动
     * @param query
     * @return
     */
    List<MarketingInfoResp> queryGrouponList(SelectMarketingRequest query);

    /**
     * 查询活动数量
     * @param query
     * @return
     */
    Long countByPageTotal(SelectMarketingRequest query);


    /**
     * 查询活动效果
     * @param query
     * @return
     */
    List<GrouponOverview> grouponByEffect(EffectPageRequest query);

    /**
     * 查询活动效果数量
     * @param query
     * @return
     */
    Long countByEffectTotal(EffectPageRequest query);

    /**
     * 查询商品
     * @param goodsInfoIds
     * @return
     */
    List<GrouponOverview> queryGoodsInfo(List<String> goodsInfoIds);


    /**
     * 查询区间内活动开始最早时间
     * @param request
     * @return
     */
    LocalDate queryEarliestActivityTime(MarketingQueryEarliestDateRequest request);
}
