package com.wanmi.ares.provider;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.request.marketing.*;
import com.wanmi.ares.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;


@FeignClient(name = "${application.ares.name}", contextId="MarketingAnalysisProvider")
public interface MarketingAnalysisProvider {

    /**
     * 营销概览-营销概况和效果分析
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/marketing-overview")
    MarketingOverview marketingOverview(@RequestBody @Valid MarketingOverviewRequest request);

    /**
     * 营销概览-支付金额趋势
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/trend-chart/money")
    List<MarketingOverview> getTrendChartForMoney(@RequestBody @Valid MarketingOverviewRequest request);

    /**
     * 营销概览-数据趋势
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/trend-chart/marketing-overview")
    List<MarketingOverview> getTrendChartForMarketingOverview(@RequestBody @Valid MarketingOverviewRequest request);

    /**
     * 营销概览-活动数据概况
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/data/situation")
    List<MarketingDataSituation> getMarketingDataSituation(@RequestBody @Valid MarketingOverviewRequest request);

    /**
     * 获取营销活动列表
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/marketing-info")
    List<MarketingInfoResp> marketingInfo(@RequestBody @Valid SelectMarketingRequest request);

    /**
     * 获取营销活动数量
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/find-marketing-total")
    Long findMarketingTotal(@RequestBody @Valid SelectMarketingRequest request);

    /**
     * 预约概况
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/overview/appointment")
    AppointmentAnalysisOverview getOverviewForAppointment(@RequestBody @Valid MarketingQueryRequest request);

    /**
     * 全款预售概况
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/overview/full-money-booking-sale")
    FullMoneyBookingReport getOverviewForFullMoneyBookingSale(@RequestBody @Valid MarketingQueryRequest request);

    /**
     * 定金预售概况
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/overview/deposit-booking-sale")
    DepositBookingReport getOverviewForDepositBooking(@RequestBody @Valid MarketingQueryRequest request);

    /**
     * 秒杀概况
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/overview/flash-sale")
    FlashSaleOverview getOverviewForFlashSale(@RequestBody @Valid MarketingQueryRequest request);

    /**
     * 拼团概况
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/overview/group")
    GrouponOverview getOverviewForGroup(@RequestBody @Valid MarketingQueryRequest request);

    /**
     * 预约数据趋势
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/trend-chart/appointment")
    List<AppointmentAnalysisOverview> getTrendChartForAppointment(@RequestBody @Valid MarketingQueryRequest request);

    /**
     * 全款预售数据趋势
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/trend-chart/full-money-booking-sale")
    List<FullMoneyBookingReport> getTrendChartForFullMoneyBookingSale(@RequestBody @Valid MarketingQueryRequest request);

    /**
     * 定金预售数据趋势
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/trend-chart/deposit-booking-sale")
    List<DepositBookingReport> getTrendChartForDepositBookingReport(@RequestBody @Valid MarketingQueryRequest request);

    /**
     * 秒杀数据趋势
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/trend-chart/flash-sale")
    List<FlashSaleOverview> getTrendChartForFlashSale(@RequestBody @Valid MarketingQueryRequest request);

    /**
     * 拼团数据趋势
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/trend-chart/group")
    List<GrouponOverview> getTrendChartForGroup(@RequestBody @Valid MarketingQueryRequest request);

    /**
     * 拼团活动效果列表
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/effect-page")
    PageInfo<GrouponOverview> effectPage(@RequestBody @Valid EffectPageRequest request);


    @PostMapping("/ares/${application.ares.version}/marketing/analysis/goods/appointment")
    List<AppointmentAnalysisForGoods> analysisAppointmentForGoods(@RequestBody @Valid MarketingQueryRequest request);

    /**
     * 分页查询秒杀活动效果-活动效果
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/goods/flash-sale")
    PageInfo<FlashSaleGoods> pageGoodsForFlashSales(@RequestBody @Valid EffectPageRequest request);

    @PostMapping("/ares/${application.ares.version}/marketing/analysis/goods/appointment-sale")
    PageInfo<AppointmentAnalysisForGoods> analysisAppointmentForGoods(@RequestBody @Valid EffectPageRequest request);

    /**
     * 分页查询秒全款预售活动效果
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/goods/query-booking-List")
    PageInfo<FullMoneyBookingReport> queryBookingByList(@RequestBody @Valid EffectPageRequest request);

    /**
     * 分页查询秒全款预售活动效果
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/goods/query-deposit-booking-List")
    PageInfo<DepositBookingReport> queryDepositBookingByList(@RequestBody @Valid EffectPageRequest request);

    /**
     * 减折赠概况
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/overview/reduce-discount-gift")
    MarketingReduceDiscountGiftReport getOverviewForReduceDiscountGift(@RequestBody @Valid MarketingQueryRequest request);


    /**
     * 减折赠数据趋势
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/trend-chart/reduce-discount-gift")
    List<MarketingReduceDiscountGiftReport> getTrendChartForReduceDiscountGift(@RequestBody @Valid MarketingQueryRequest request);

    /**
     * 减折赠活动效果
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/goods/reduce-discount-gift")
    PageInfo<MarketingReduceDiscountGiftReport> getActivityEffectForReduceDiscountGift(@RequestBody @Valid EffectPageRequest request);


    /**
     * 砍价活动概况
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/overview/goods-bargain")
    GoodsBargainReport getOverviewForGoodsBargain(@RequestBody @Valid MarketingQueryRequest request);


    /**
     * 砍价活动数据趋势
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/trend-chart/goods-bargain")
    List<GoodsBargainOverview> getTrendChartForGoodsBargain(@RequestBody @Valid MarketingQueryRequest request);

    /**
     * 砍价活动效果
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/goods-bargain")
    PageInfo<GoodsBargainReport> getActivityEffectForGoodsBargain(@RequestBody @Valid EffectPageRequest request);

    /**
     * 减折赠活动效果
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/marketing/analysis/goods/suits")
    PageInfo<SuitsReport> getActivityEffectForSiuts(@RequestBody @Valid EffectPageRequest request);
}
