package com.wanmi.ares.marketing.reducediscountgift.dao;

import com.wanmi.ares.request.marketing.*;
import com.wanmi.ares.response.MarketingInfoResp;
import com.wanmi.ares.response.MarketingReduceDiscountGiftReport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * 减折赠
 * @author edz
 */
@Repository
public interface MarketingReduceDiscountGiftMapper {

    /**
     * 按订单与营销维度根据指定时间/默认昨天统计:营销支付金额 + 营销优惠金额 + 营销支付订单数 + 营销支付人数 + 营销支付件数 + 新老客户(平台/商家)
     * @param request
     * @return
     */
    int insertMarketingStatisticsDay(MarketingAnalysisJobRequest request);

    /**
     * 按订单与SKU编号维度每日统计:会员ID + 店铺ID + 营销ID + 购买数量 + 订单实际支付金额 + 优惠金额 + 支付时间
     * @param request
     * @return
     */
    int insertTradeMarketingSkuDetailDay(MarketingAnalysisJobRequest request);

    /**
     * 根据营销类型、店铺名称、营销名称查询营销信息
     * @param request
     * @return
     */
    List<MarketingInfoResp> findMarketing(SelectMarketingRequest request);

    /**
     * 查询营销活动总数
     * @param request
     * @return
     */
    Long countByPageTotal(SelectMarketingRequest request);

    /**
     * 查询区间内活动开始最早时间
     * @param request
     * @return
     */
    LocalDate queryEarliestActivityTime(MarketingQueryEarliestDateRequest request);

    /**
     * 根据统计时间、店铺ID、营销ID查询营销统计信息
     * @param request
     * @return
     */
    MarketingReduceDiscountGiftReport getMarketingOverviewByStoreIdAndMarketing(MarketingQueryRequest request);

    /**
     * 查询数据趋势（按天统计）：支付ROI & 营销支付金额 & 营销优惠金额 & 营销支付件数 & 营销支付订单数 & 连带率 & 营销支付人数 & 客单价
     * @param request
     * @return
     */
    List<MarketingReduceDiscountGiftReport> getMarketingTrendChartDayByStoreIdAndMarketing(MarketingQueryRequest request);

    /**
     * 查询数据趋势（按周统计）：支付ROI & 营销支付金额 & 营销优惠金额 & 营销支付件数 & 营销支付订单数 & 连带率 & 营销支付人数 & 客单价
     * @param request
     * @return
     */
    List<MarketingReduceDiscountGiftReport> getMarketingTrendChartWeekByStoreIdAndMarketing(MarketingQueryRequest request);

    /**
     * 活动效果
     * @param request
     * @return
     */
    List<MarketingReduceDiscountGiftReport> pageActivityEffect(EffectPageRequest request);
}
