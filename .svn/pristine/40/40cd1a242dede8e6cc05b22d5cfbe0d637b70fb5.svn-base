package com.wanmi.ares.marketing.overview.service;

import com.wanmi.ares.enums.DefaultFlag;
import com.wanmi.ares.marketing.overview.dao.MarketingOverviewMapper;
import com.wanmi.ares.marketing.overview.dao.MarketingSituationMapper;
import com.wanmi.ares.request.marketing.MarketingOverviewRequest;
import com.wanmi.ares.response.MarketingDataSituation;
import com.wanmi.ares.response.MarketingOverview;
import com.wanmi.ares.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.wanmi.ares.utils.DateUtil.formatLocalDate;

@Service
public class MarketingOverviewService {

    @Autowired
    private MarketingOverviewMapper marketingOverviewMapper;

    @Autowired
    private MarketingSituationMapper marketingSituationMapper;

    @Transactional
    public void insert() {
        marketingOverviewMapper.insertOfDay();
        marketingOverviewMapper.delOfSevenDay();
        marketingOverviewMapper.insertOfSevenDay();
        marketingOverviewMapper.delOfThirtyDay();
        marketingOverviewMapper.insertOfThirtyDay();

        // 每月1号执行一次
        if (LocalDate.now().getDayOfMonth() == 1) {
            marketingOverviewMapper.insertOfMonth();
        }
    }

    public MarketingOverview info(MarketingOverviewRequest request){
        MarketingOverview result = null;
        switch (request.getStatisticsDataType()){
            case YESTERDAY:
                MarketingOverview overviewOfDay = marketingOverviewMapper.findByDay(request);
                marketingSituationMapper.findByDay(request).forEach(marketingDataSituation -> activityNum(overviewOfDay, marketingDataSituation));
                result = overviewOfDay;
                break;
            case SEVEN:
                MarketingOverview overviewOfSeven = marketingOverviewMapper.findBySeven(request);
                marketingSituationMapper.findBySeven(request).forEach(marketingDataSituation -> activityNum(overviewOfSeven, marketingDataSituation));
                result = overviewOfSeven;
                break;
            case THIRTY:
                MarketingOverview overviewOfThirty = marketingOverviewMapper.findByThirty(request);
                marketingSituationMapper.findByThirty(request).forEach(marketingDataSituation -> activityNum(overviewOfThirty, marketingDataSituation));
                result = overviewOfThirty;
                break;
            case MONTH:
                MarketingOverview overviewOfMonth = marketingOverviewMapper.findByMonth(request);
                marketingSituationMapper.findByMonth(request).forEach(marketingDataSituation -> activityNum(overviewOfMonth, marketingDataSituation));
                result = overviewOfMonth;
                break;
            default:
                break;
        }
        return result;
    }

    private void activityNum(MarketingOverview overview, MarketingDataSituation marketingDataSituation){
        if(Objects.isNull(overview) || Objects.isNull(marketingDataSituation)){
            return;
        }
        switch (marketingDataSituation.getMarketingType()){
            case COUPON:
                overview.setCouponCount(marketingDataSituation.getMarketingActivityCount());
                break;
            case GROUPON:
                overview.setGrouponCount(marketingDataSituation.getMarketingActivityCount());
                break;
            case FLASH_SALE:
                overview.setFlashSaleCount(marketingDataSituation.getMarketingActivityCount());
                break;
            case REDUCTION_DISCOUNT_GIFT:
                overview.setFullSubtractDiscountCount(marketingDataSituation.getMarketingActivityCount());
                break;
            case APPOINTMENT_SALE:
                overview.setAppointmentCount(marketingDataSituation.getMarketingActivityCount());
                break;
            case BOOKING_SALE:
                overview.setBookingSaleCount(marketingDataSituation.getMarketingActivityCount());
                break;
            case BUYOUT_PRICE:
                overview.setBuyoutPriceCount(marketingDataSituation.getMarketingActivityCount());
                break;
            case HALF_PRICE_SECOND_PIECE:
                overview.setHalfPriceSecondPieceCount(marketingDataSituation.getMarketingActivityCount());
                break;
            case SUITS:
                overview.setSuitsCount(marketingDataSituation.getMarketingActivityCount());
                break;
            case GOODS_BARGAIN:
                overview.setBargainCount(marketingDataSituation.getMarketingActivityCount());
                break;
            case PREFERENTIAL:
                overview.setPreferentialCount(marketingDataSituation.getMarketingActivityCount());
                break;
            default:
                break;
        }
    }

    public List<MarketingOverview> moneyOfTrendChart(MarketingOverviewRequest request){
        return marketingOverviewMapper.findMoneyTrendChart(request).stream().peek(i -> {
            i.setTitle(formatLocalDate(i.getDate(),DateUtil.FMT_DATE_3)+DateUtil.getWeekStr(i.getDate()));
        }).collect(Collectors.toList());
    }

    public List<MarketingOverview> dataOfTrendChart(MarketingOverviewRequest request){
        List<MarketingOverview> list = null;
        if (DefaultFlag.YES == request.getWeek()){
            list = marketingOverviewMapper.findTrendChartByWeek(request);
        } else {
            list = marketingOverviewMapper.findTrendChart(request).stream().peek(i -> {
                i.setTitle(formatLocalDate(i.getDate(),DateUtil.FMT_DATE_3)+DateUtil.getWeekStr(i.getDate()));
            }).collect(Collectors.toList());
        }
        return list;
    }
}
