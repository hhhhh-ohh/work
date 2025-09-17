package com.wanmi.sbc.marketing.analysis;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.provider.MarketingAnalysisProvider;
import com.wanmi.ares.request.marketing.EffectPageRequest;
import com.wanmi.ares.request.marketing.MarketingQueryRequest;
import com.wanmi.ares.request.marketing.SelectMarketingRequest;
import com.wanmi.ares.response.MarketingAnalysisBase;
import com.wanmi.ares.response.MarketingInfoResp;
import com.wanmi.sbc.common.base.BaseResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Tag(name = "AnalysisReportController", description = "营销活动分析")
@RestController
@Validated
@RequestMapping("/marketing/analysis")
public class AnalysisReportController {
    @Autowired
    private MarketingAnalysisProvider marketingAnalysisProvider;

    /**
     * 获取营销活动列表
     * @return
     */
    @Operation(summary = "获取营销活动列表")
    @PostMapping(value = "/marketingInfo")
    public BaseResponse<PageImpl<MarketingInfoResp>> marketingInfo(@RequestBody SelectMarketingRequest request){
        List<MarketingInfoResp> page = marketingAnalysisProvider.marketingInfo(request);
        Long pageTotal = marketingAnalysisProvider.findMarketingTotal(request);
        return BaseResponse.success(new PageImpl<MarketingInfoResp>(page, PageRequest.of(request.getPageNum(), request.getPageSize()),pageTotal));
    }

    /**
     * 获取营销活动列表
     */
    @Operation(summary = "获取营销活动概况")
    @PostMapping(value = "/overview")
    public BaseResponse<MarketingAnalysisBase> getOverview(@RequestBody MarketingQueryRequest request) {
        MarketingAnalysisBase overview = null;
        switch (request.getMarketingType()) {
            case APPOINTMENT_SALE:
                overview = marketingAnalysisProvider.getOverviewForAppointment(request);
                break;
            case FULL_MONEY_BOOKING_SALE:
                overview = marketingAnalysisProvider.getOverviewForFullMoneyBookingSale(request);
                break;
            case DEPOSIT_BOOKING_SALE:
                overview = marketingAnalysisProvider.getOverviewForDepositBooking(request);
                break;
            case FLASH_SALE:
                overview = marketingAnalysisProvider.getOverviewForFlashSale(request);
                break;
            case GROUPON:
                overview = marketingAnalysisProvider.getOverviewForGroup(request);
                break;
            case REDUCTION_DISCOUNT_GIFT:
                overview = marketingAnalysisProvider.getOverviewForReduceDiscountGift(request);
                break;
            case GOODS_BARGAIN:
                overview = marketingAnalysisProvider.getOverviewForGoodsBargain(request);
                break;
            case BUYOUT_PRICE:
                overview = marketingAnalysisProvider.getOverviewForReduceDiscountGift(request);
                break;
            case HALF_PRICE_SECOND_PIECE:
                overview = marketingAnalysisProvider.getOverviewForReduceDiscountGift(request);
                break;
            case SUITS:
                overview = marketingAnalysisProvider.getOverviewForReduceDiscountGift(request);
                break;
            case PREFERENTIAL:
                overview = marketingAnalysisProvider.getOverviewForReduceDiscountGift(request);
                break;
            default:
                break;
        }
        if (Objects.isNull(overview)){
            overview = new MarketingAnalysisBase();
        }
        overview.init();
        return BaseResponse.success(overview);
    }

    /**
     * 获取营销活动列表
     */
    @Operation(summary = "获取营销活动趋势")
    @PostMapping(value = "/overviewForTrendChart")
    public BaseResponse<List<? extends MarketingAnalysisBase>> getOverviewForTrendChart(@RequestBody MarketingQueryRequest request){
        List<? extends MarketingAnalysisBase> analysisOverviews = Collections.emptyList();
        switch (request.getMarketingType()){
            case APPOINTMENT_SALE:
                analysisOverviews = marketingAnalysisProvider.getTrendChartForAppointment(request);
                break;
            case FULL_MONEY_BOOKING_SALE:
                analysisOverviews = marketingAnalysisProvider.getTrendChartForFullMoneyBookingSale(request);
                break;
            case DEPOSIT_BOOKING_SALE:
                analysisOverviews = marketingAnalysisProvider.getTrendChartForDepositBookingReport(request);
                break;
            case FLASH_SALE:
                analysisOverviews = marketingAnalysisProvider.getTrendChartForFlashSale(request);
                break;
            case GROUPON:
                analysisOverviews = marketingAnalysisProvider.getTrendChartForGroup(request);
                break;
            case REDUCTION_DISCOUNT_GIFT:
                analysisOverviews = marketingAnalysisProvider.getTrendChartForReduceDiscountGift(request);
                break;
            case GOODS_BARGAIN:
                analysisOverviews = marketingAnalysisProvider.getTrendChartForGoodsBargain(request);
                break;
            case BUYOUT_PRICE:
                analysisOverviews = marketingAnalysisProvider.getTrendChartForReduceDiscountGift(request);
                break;
            case HALF_PRICE_SECOND_PIECE:
                analysisOverviews = marketingAnalysisProvider.getTrendChartForReduceDiscountGift(request);
                break;
            case SUITS:
                analysisOverviews = marketingAnalysisProvider.getTrendChartForReduceDiscountGift(request);
                break;
            case PREFERENTIAL:
                analysisOverviews = marketingAnalysisProvider.getTrendChartForReduceDiscountGift(request);
                break;
            default:
                break;
        }
        analysisOverviews.forEach(v -> {
            v.setTitle(Objects.nonNull(v.getDayInfo()) ? v.getDayInfo() : v.getWeekInfo());
        });
        return BaseResponse.success(analysisOverviews);
    }

    /**
     * 获取活动效果列表
     * @return
     */
    @Operation(summary = "获取活动效果列表")
    @PostMapping(value = "/effect-page")
    public BaseResponse<PageInfo<? extends MarketingAnalysisBase>> marketingInfo(@RequestBody EffectPageRequest request){
        PageInfo<? extends MarketingAnalysisBase> analysisOverviews = new PageInfo<>(Collections.emptyList());
        switch (request.getMarketingType()){
            case APPOINTMENT_SALE:
                analysisOverviews = marketingAnalysisProvider.analysisAppointmentForGoods(request);
                break;
            case FULL_MONEY_BOOKING_SALE:
                analysisOverviews = marketingAnalysisProvider.queryBookingByList(request);
                break;
            case DEPOSIT_BOOKING_SALE:
                analysisOverviews = marketingAnalysisProvider.queryDepositBookingByList(request);
                break;
            case FLASH_SALE:
                analysisOverviews = marketingAnalysisProvider.pageGoodsForFlashSales(request);
                break;
            case GROUPON:
                analysisOverviews = marketingAnalysisProvider.effectPage(request);
                break;
            case REDUCTION_DISCOUNT_GIFT:
                analysisOverviews = marketingAnalysisProvider.getActivityEffectForReduceDiscountGift(request);
                break;
            case GOODS_BARGAIN:
                analysisOverviews = marketingAnalysisProvider.getActivityEffectForGoodsBargain(request);
                break;
            case BUYOUT_PRICE:
                analysisOverviews = marketingAnalysisProvider.getActivityEffectForReduceDiscountGift(request);
                break;
            case HALF_PRICE_SECOND_PIECE:
                analysisOverviews = marketingAnalysisProvider.getActivityEffectForReduceDiscountGift(request);
                break;
            case SUITS:
                analysisOverviews = marketingAnalysisProvider.getActivityEffectForSiuts(request);
                break;
            case PREFERENTIAL:
                analysisOverviews = marketingAnalysisProvider.getActivityEffectForReduceDiscountGift(request);
                break;
            default:
                break;
        }
        analysisOverviews.getList().forEach(MarketingAnalysisBase::init);
        return BaseResponse.success(analysisOverviews);
    }

}
