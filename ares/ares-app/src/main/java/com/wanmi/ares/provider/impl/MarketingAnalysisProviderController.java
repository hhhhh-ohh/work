package com.wanmi.ares.provider.impl;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.common.DataPeriod;
import com.wanmi.ares.marketing.appointment.service.AppointmentSaleService;
import com.wanmi.ares.marketing.bargain.GoodsBargainService;
import com.wanmi.ares.marketing.bookingsale.service.BookingSaleService;
import com.wanmi.ares.marketing.flashsale.service.FlashSaleService;
import com.wanmi.ares.marketing.groupon.service.GrouponService;
import com.wanmi.ares.marketing.marketingtaskrecord.dao.MarketingTaskRecordMapper;
import com.wanmi.ares.marketing.overview.service.MarketingOverviewService;
import com.wanmi.ares.marketing.overview.service.MarketingSituationService;
import com.wanmi.ares.marketing.preferential.service.PreferentialService;
import com.wanmi.ares.marketing.reducediscountgift.service.MarketingReduceDiscountGiftService;
import com.wanmi.ares.marketing.suits.service.SuitsService;
import com.wanmi.ares.provider.MarketingAnalysisProvider;
import com.wanmi.ares.request.marketing.*;
import com.wanmi.ares.response.*;
import com.wanmi.sbc.common.util.KsBeanUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhongjichuan
 * @date 2021-01-12 14:23
 */
@RestController
public class MarketingAnalysisProviderController implements MarketingAnalysisProvider {

    @Autowired
    private AppointmentSaleService appointmentSaleService;

    @Autowired
    private BookingSaleService bookingSaleService;

    @Autowired
    private FlashSaleService flashSaleService;

    @Autowired
    private GrouponService grouponService;

    @Autowired
    private MarketingOverviewService marketingOverviewService;

    @Autowired
    private MarketingReduceDiscountGiftService marketingReduceDiscountGiftService;

    @Autowired
    private MarketingSituationService marketingSituationService;

    @Autowired
    private MarketingTaskRecordMapper marketingTaskRecordMapper;

    @Autowired
    private GoodsBargainService goodsBargainService;

    @Autowired
    private PreferentialService preferentialService;

    @Autowired
    private SuitsService suitsService;

    @Override
    public MarketingOverview marketingOverview(@RequestBody @Valid MarketingOverviewRequest request) {
        return marketingOverviewService.info(request);
    }

    @Override
    public List<MarketingOverview> getTrendChartForMoney(@RequestBody @Valid MarketingOverviewRequest request) {
        return marketingOverviewService.moneyOfTrendChart(request);
    }

    @Override
    public List<MarketingOverview> getTrendChartForMarketingOverview(@RequestBody @Valid MarketingOverviewRequest request) {
        return marketingOverviewService.dataOfTrendChart(request);
    }

    @Override
    public List<MarketingDataSituation> getMarketingDataSituation(@RequestBody @Valid MarketingOverviewRequest request) {
        return marketingSituationService.findMarketingDataSituationList(request);
    }

    @Override
    public List<MarketingInfoResp> marketingInfo(@RequestBody @Valid SelectMarketingRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        List<MarketingInfoResp> resp = new ArrayList<>();
        switch (request.getMarketingType()){
            case APPOINTMENT_SALE:
                resp = appointmentSaleService.pageForAppointmentSale(request);
                break;
            case FULL_MONEY_BOOKING_SALE:
                resp = bookingSaleService.findMarketing(request);
                break;
            case DEPOSIT_BOOKING_SALE:
                resp = bookingSaleService.findMarketing(request);
                break;
            case FLASH_SALE:
                resp = flashSaleService.pageForFlashSale(request);
                break;
            case GROUPON:
                resp = grouponService.findMarketing(request);
                break;
            case REDUCTION_DISCOUNT_GIFT:
            case SUITS:
            case HALF_PRICE_SECOND_PIECE:
            case BUYOUT_PRICE:
            case PREFERENTIAL:
                resp = marketingReduceDiscountGiftService.findMarketing(request);
                break;
            case GOODS_BARGAIN:
                resp = goodsBargainService.findMarketing(request);
                break;
            default:
                break;
        }
        return resp;
    }

    @Override
    public Long findMarketingTotal(@RequestBody @Valid SelectMarketingRequest request){
        request.setQueryDate(DataPeriod.getStartDate());
        Long total = NumberUtils.LONG_ZERO;
        switch (request.getMarketingType()){
            case APPOINTMENT_SALE:
                total = appointmentSaleService.countTotal(request);
                break;
            case FULL_MONEY_BOOKING_SALE:
                total = bookingSaleService.findMarketingTotal(request);
                break;
            case DEPOSIT_BOOKING_SALE:
                total = bookingSaleService.findMarketingTotal(request);
                break;
            case FLASH_SALE:
                total = flashSaleService.countTotal(request);
                break;
            case GROUPON:
                total = grouponService.findMarketingTotal(request);
                break;
            case REDUCTION_DISCOUNT_GIFT:
            case SUITS:
            case HALF_PRICE_SECOND_PIECE:
            case BUYOUT_PRICE:
            case PREFERENTIAL:
                total = marketingReduceDiscountGiftService.findMarketingTotal(request);
                break;
            case GOODS_BARGAIN:
                total = goodsBargainService.findMarketingTotal(request);
                break;
            default:
                break;
        }
        return total;
    }


    @Override
    public AppointmentAnalysisOverview getOverviewForAppointment(@Valid MarketingQueryRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        return appointmentSaleService.analysisOverview(request);
    }

    @Override
    public FullMoneyBookingReport getOverviewForFullMoneyBookingSale(@Valid MarketingQueryRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        return bookingSaleService.queryFullMoneyBookingOverView(request);
    }

    @Override
    public DepositBookingReport getOverviewForDepositBooking(@Valid MarketingQueryRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        return bookingSaleService.queryDepositBookingOverView(request);
    }

    @Override
    public FlashSaleOverview getOverviewForFlashSale(@Valid MarketingQueryRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        return flashSaleService.queryFlashSaleOverView(request);
    }

    @Override
    public GrouponOverview getOverviewForGroup(@Valid MarketingQueryRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        return grouponService.queryGrouponOverView(request);
    }

    @Override
    public List<AppointmentAnalysisOverview> getTrendChartForAppointment(@Valid MarketingQueryRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        SelectMarketingRequest selectMarketingRequest = new SelectMarketingRequest();
        KsBeanUtil.copyPropertiesThird(request, selectMarketingRequest);
        Long total = this.findMarketingTotal(selectMarketingRequest);
        //如果活动数量为0，返回空数据
        if(total == 0){
            return Collections.emptyList();
        }
        return appointmentSaleService.analysisOverviewForTrendChart(request);
    }

    @Override
    public List<FullMoneyBookingReport> getTrendChartForFullMoneyBookingSale(@Valid MarketingQueryRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        if(!checkMarketingCount(request)){
            return Collections.emptyList();
        }
        return bookingSaleService.queryFullMoneyBookingForTrend(request);
    }

    @Override
    public List<DepositBookingReport> getTrendChartForDepositBookingReport(@Valid MarketingQueryRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        if(!checkMarketingCount(request)){
            return Collections.emptyList();
        }
        return bookingSaleService.queryDepositBookingForTrend(request);
    }

    @Override
    public List<FlashSaleOverview> getTrendChartForFlashSale(@Valid MarketingQueryRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        if(!checkMarketingCount(request)){
            return Collections.emptyList();
        }
        return flashSaleService.queryFlashSaleTrend(request);
    }

    @Override
    public List<GrouponOverview> getTrendChartForGroup(@Valid MarketingQueryRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        if(!checkMarketingCount(request)){
            return Collections.emptyList();
        }
        return grouponService.queryGrouponTrend(request);
    }

    @Override
    public PageInfo<AppointmentAnalysisForGoods> analysisAppointmentForGoods(@Valid @RequestBody EffectPageRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        return appointmentSaleService.analysisForGoods(request);
    }

    @Override
    public PageInfo<FullMoneyBookingReport> queryBookingByList(@Valid @RequestBody EffectPageRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        return bookingSaleService.queryBookingByList(request);
    }

    @Override
    public PageInfo<DepositBookingReport> queryDepositBookingByList(@Valid EffectPageRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        return bookingSaleService.queryDepositBookingByList(request);
    }

    @Override
    public PageInfo<GrouponOverview> effectPage(@Valid EffectPageRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        return grouponService.effectPage(request);
    }

    @Override
    public List<AppointmentAnalysisForGoods> analysisAppointmentForGoods(@Valid MarketingQueryRequest request) {
        return Collections.emptyList();
    }

    @Override
    public PageInfo<FlashSaleGoods> pageGoodsForFlashSales(@RequestBody @Valid EffectPageRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        return flashSaleService.pageGoodsForFlashSales(request);
    }

    /**
     * 减折赠概况
     * @param request
     * @return
     */
    @Override
    public MarketingReduceDiscountGiftReport getOverviewForReduceDiscountGift(@RequestBody @Valid MarketingQueryRequest request){
        request.setQueryDate(DataPeriod.getStartDate());
        return marketingReduceDiscountGiftService.getMarketingOverviewByStoreIdAndMarketing(request);
    }


    /**
     * 减折赠数据趋势
     * @param request
     * @return
     */
    @Override
    public List<MarketingReduceDiscountGiftReport> getTrendChartForReduceDiscountGift(@RequestBody @Valid MarketingQueryRequest request){
        request.setQueryDate(DataPeriod.getStartDate());
        if(!checkMarketingCount(request)){
            return Collections.emptyList();
        }
        return marketingReduceDiscountGiftService.getMarketingTrendChartByStoreIdAndMarketing(request);
    }

    /**
     * 减折赠活动效果
     * @param request
     * @return
     */
    @Override
    public PageInfo<MarketingReduceDiscountGiftReport> getActivityEffectForReduceDiscountGift(@RequestBody @Valid EffectPageRequest request){
        request.setQueryDate(DataPeriod.getStartDate());
        return marketingReduceDiscountGiftService.pageActivityEffect(request);
    }

    /**
     * 砍价活动营销概况
     * @param request
     * @return
     */
    @Override
    public GoodsBargainReport getOverviewForGoodsBargain(@Valid MarketingQueryRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        return goodsBargainService.queryBargainGoodsOverView(request);
    }

    /**
     * 活动数据趋势图
     * @param request
     * @return
     */
    @Override
    public List<GoodsBargainOverview> getTrendChartForGoodsBargain(@Valid MarketingQueryRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        if(!checkMarketingCount(request)){
            return Collections.emptyList();
        }
        return goodsBargainService.queryBargainTrend(request);
    }

    /**
     * 砍价活动效果
     * @param request
     * @return
     */
    @Override
    public PageInfo<GoodsBargainReport> getActivityEffectForGoodsBargain(@Valid EffectPageRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        return goodsBargainService.queryGoodsForBargainGoods(request);
    }

    /**
     * 活动数量大于0返回true
     * @param request
     * @return
     */
    public boolean checkMarketingCount(MarketingQueryRequest request){
        SelectMarketingRequest selectMarketingRequest = new SelectMarketingRequest();
        KsBeanUtil.copyPropertiesThird(request, selectMarketingRequest);
        Long total = this.findMarketingTotal(selectMarketingRequest);
        //如果活动数量为0，返回空数据
        if(total == 0){
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate originDate = LocalDate.parse(request.getQueryDate(), formatter);
        MarketingTaskRecordQueryRequest recordQueryRequest = new MarketingTaskRecordQueryRequest();
        recordQueryRequest.setMarketingType(request.getMarketingType().toValue());
        recordQueryRequest.setCreateTimeBegin(originDate);
        //查询定时任务记录数量
        Long recordCount = marketingTaskRecordMapper.count(recordQueryRequest);
        //如果没有初始化，返回空数据
        if(recordCount == 0){
            return false;
        }
        return true;
    }

    /**
     * 组合购活动效果
     * @param request
     * @return
     */
    @Override
    public PageInfo<SuitsReport> getActivityEffectForSiuts(@Valid EffectPageRequest request) {
        request.setQueryDate(DataPeriod.getStartDate());
        return suitsService.pageActivityEffect(request);
    }
}
