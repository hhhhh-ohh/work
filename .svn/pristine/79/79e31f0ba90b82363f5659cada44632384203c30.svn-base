package com.wanmi.ares.marketing.commonservice;

import com.wanmi.ares.marketing.appointment.dao.AppointmentStatisticsMapper;
import com.wanmi.ares.marketing.bookingsale.dao.BookingSaleStatisticsMapper;
import com.wanmi.ares.marketing.flashsale.dao.FlashSaleStatisticsMapper;
import com.wanmi.ares.marketing.groupon.dao.GrouponStatisticsMapper;
import com.wanmi.ares.marketing.marketingtaskrecord.dao.MarketingTaskRecordMapper;
import com.wanmi.ares.marketing.marketingtaskrecord.model.MarketingTaskRecord;
import com.wanmi.ares.marketing.reducediscountgift.dao.MarketingReduceDiscountGiftMapper;
import com.wanmi.ares.request.marketing.MarketingQueryEarliestDateRequest;
import com.wanmi.ares.request.marketing.MarketingQueryRequest;
import com.wanmi.ares.request.marketing.MarketingTaskRecordQueryRequest;
import com.wanmi.ares.response.MarketingAnalysisBase;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.ares.utils.WeekDate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AnalysisCommonService {

    @Autowired
    protected FlashSaleStatisticsMapper flashSaleStatisticsMapper;

    @Autowired
    protected MarketingTaskRecordMapper marketingTaskRecordMapper;

    @Autowired
    protected GrouponStatisticsMapper grouponStatisticsMapper;

    @Autowired
    protected BookingSaleStatisticsMapper bookingSaleStatisticsMapper;

    @Autowired
    protected AppointmentStatisticsMapper appointmentStatisticsMapper;

    @Autowired
    private MarketingReduceDiscountGiftMapper marketingReduceDiscountGiftMapper;

    public void initOverview(MarketingAnalysisBase overview) {
        overview.setPayMoney(BigDecimal.ZERO);
        overview.setDiscountMoney(BigDecimal.ZERO);
        overview.setPayGoodsCount(0L);
        overview.setPayTradeCount(0L);
        overview.setJointRate(BigDecimal.ZERO);
        overview.setNewCustomerCount(0L);
        overview.setOldCustomerCount(0L);
        overview.setPayCustomerCount(0L);
        overview.setCustomerPrice(BigDecimal.ZERO);
    }

    /**
     * 营销分析-当天没有数据时，自动补充空数据
     *
     * @param overviewList
     * @param overviewClass
     * @return
     */
    public List<? extends MarketingAnalysisBase> fullDataForDay(List<? extends MarketingAnalysisBase> overviewList,
                                                                Class<? extends MarketingAnalysisBase> overviewClass,
                                                                MarketingQueryRequest query) {
        // 日期范围内活动最早时间
        LocalDate earliestDate = this.queryEarliestActivityTime(query);
        // 需要返回的日期范围
        List<LocalDate> dateList = DateUtil.getDatesBetweenByStartAndEnd(earliestDate, LocalDate.now().minusDays(1));
        // 查询日期范围内符合的数据
        List<LocalDate> days = overviewList.stream().map(MarketingAnalysisBase::getDay).collect(Collectors.toList());

        MarketingTaskRecordQueryRequest recordQueryRequest = new MarketingTaskRecordQueryRequest();
        recordQueryRequest.setMarketingType(query.getMarketingType().toValue());
        recordQueryRequest.setCreateTimeBegin(earliestDate);
        // 定时任务执行记录
        List<MarketingTaskRecord> recordList = marketingTaskRecordMapper.list(recordQueryRequest);
        // 定时任务执行记录日期
        List<LocalDate> recordDays = recordList.stream().map(MarketingTaskRecord::getCreateTime).collect(Collectors.toList());
        // 定时任务执行失败的日期
        List<LocalDate> jumpList = dateList.stream().filter(date -> !recordDays.contains(date)).collect(Collectors.toList());
        dateList.removeAll(jumpList);
        // 取日期差集，这些日期当天没有订单数据，为这些日期补充空数据
        dateList.removeAll(days);
        List noDataOverviewList = dateList.stream().map(date -> {
            MarketingAnalysisBase overview = null;
            try {
                overview = overviewClass.newInstance();
                overview.setDay(date);
                this.initOverview(overview);
            } catch (InstantiationException | IllegalAccessException e) {
               log.error("initOverview执行出错:{}",e.fillInStackTrace());
            }
            return overview;
        }).collect(Collectors.toList());
        overviewList.addAll(noDataOverviewList);
        return overviewList.stream().map(v -> {
            v.setDayInfo(v.getDay().toString().replace('-', '/').concat(DateUtil.getWeekStr(v.getDay())));
            return v;
        }).sorted(Comparator.comparing(MarketingAnalysisBase::getDay)).collect(Collectors.toList());
    }

    /**
     * 营销分析-一周内没有数据时，自动补充空数据
     * pc: 注意子类xxxAnalysisOverview中的属性会丢失
     *
     * @param baseOverviewList
     * @param overviewClass
     * @return
     */
    public List<MarketingAnalysisBase> fullOverviewForWeek(List<MarketingAnalysisBase> baseOverviewList,
                                                           Class<? extends MarketingAnalysisBase> overviewClass,
                                                           MarketingQueryRequest query) {
        LocalDate now = LocalDate.now();
        // 日期范围内活动最早时间
        LocalDate earliestDate = this.queryEarliestActivityTime(query);
        // 得到的开始日期是周一,得到的开始日期不是周一，则第一个WeekDate的beginDate为入参
        List<WeekDate> weekDates = DateUtil.getWeekLastDay(earliestDate, LocalDate.now().minusDays(1));
        if (CollectionUtils.isNotEmpty(weekDates)) {
            WeekDate weekDate = weekDates.get(0);
            weekDate.setBeginDate(weekDate.getBeginDate().with(WeekFields.ISO.dayOfWeek(), 1));
        }

        List<MarketingAnalysisBase> fulledOverviewList = new ArrayList<>();

        // <自然周的周一日期，该周的概况>
        Map<LocalDate, MarketingAnalysisBase> analysisOverviewMap = baseOverviewList.stream()
                .collect(Collectors.toMap((v) -> {
                    String[] yearAndWeek = v.getWeek().split("-");
                    LocalDate localDate = now.with(WeekFields.ISO.weekBasedYear(), Long.parseLong(yearAndWeek[0]))
                            .with(WeekFields.ISO.weekOfYear(), Long.parseLong(yearAndWeek[1]));
                    // 获得自然周周一的日期
                    return localDate.with(WeekFields.ISO.dayOfWeek(), 1);
                }, Function.identity()));

        // 为没有数据的自然周初始化数据
        for (WeekDate weekDate : weekDates) {
            MarketingAnalysisBase overview = analysisOverviewMap.get(weekDate.getBeginDate());
            if (weekDate.getBeginDate().isEqual(earliestDate.with(WeekFields.ISO.dayOfWeek(), 1))) {
                weekDate.setBeginDate(earliestDate);
            }
            if (Objects.isNull(overview)) {
                MarketingAnalysisBase newOverview = null;
                try {
                    newOverview = overviewClass.newInstance();
                    this.initOverview(newOverview);
                    newOverview.setWeekInfo(weekDate.getBeginDate().toString().replace('-', '/')
                            .concat("~")
                            .concat(weekDate.getEndDate().toString().replace('-', '/')));
                    newOverview.setWeek(DateUtil.getWeekOfYear(weekDate.getBeginDate()));
                    fulledOverviewList.add(newOverview);
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("newOverview执行出错:{}",e.fillInStackTrace());
                }
            } else {
                overview.setWeekInfo(weekDate.getBeginDate().toString().replace('-', '/')
                        .concat("~")
                        .concat(weekDate.getEndDate().toString().replace('-', '/')));
                fulledOverviewList.add(overview);
            }
        }
        return fulledOverviewList.stream().sorted(Comparator.comparing(MarketingAnalysisBase::getWeek)).collect(Collectors.toList());
    }

    /**
     * 查询日期范围内或所选活动中营销活动最早开始时间
     *
     * @param query
     * @return
     */
    protected LocalDate queryEarliestActivityTime(MarketingQueryRequest query) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 日期范围开始日期 如：90天前的日期
        LocalDate originDate = LocalDate.parse(query.getQueryDate(), formatter);
        MarketingQueryEarliestDateRequest request = new MarketingQueryEarliestDateRequest();
        KsBeanUtil.copyPropertiesThird(query, request);
        // 日期范围内或所选活动中营销活动最早开始时间
        LocalDate baseDate = null;
        switch (request.getMarketingType()) {
            case APPOINTMENT_SALE:
                baseDate = appointmentStatisticsMapper.queryEarliestActivityTime(request);
                break;
            case FULL_MONEY_BOOKING_SALE:
                baseDate = bookingSaleStatisticsMapper.queryEarliestActivityTime(request);
                break;
            case DEPOSIT_BOOKING_SALE:
                baseDate = bookingSaleStatisticsMapper.queryEarliestActivityTime(request);
                break;
            case FLASH_SALE:
                baseDate = flashSaleStatisticsMapper.queryEarliestActivityTime(request);
                break;
            case GROUPON:
                baseDate = grouponStatisticsMapper.queryEarliestActivityTime(request);
                break;
            case REDUCTION_DISCOUNT_GIFT:
                baseDate = marketingReduceDiscountGiftMapper.queryEarliestActivityTime(request);
                break;
            default:
                break;
        }
        //查询定时任务最早时间
        MarketingTaskRecordQueryRequest recordQueryRequest = new MarketingTaskRecordQueryRequest();
        recordQueryRequest.setMarketingType(query.getMarketingType().toValue());
        recordQueryRequest.setCreateTimeBegin(originDate);
        LocalDate recordTime = marketingTaskRecordMapper.queryEarliestTime(recordQueryRequest);
        // 如果日期范围内的最早日期大于日期范围开始时间，则用日期范围内的最早时间
        if (Objects.nonNull(baseDate) && originDate.isBefore(baseDate)) {
            originDate = baseDate;
        }
        if (Objects.nonNull(recordTime) && originDate.isBefore(recordTime)) {
            originDate = recordTime;
        }
        return originDate;
    }

    /**
     * 百分比格式化
     *
     * @param num 0.6666666666
     * @return 66.66%
     */
    public static String formatPercent(BigDecimal num) {
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMaximumFractionDigits(2);//设置百分数最多保留两位小数
        nt.setRoundingMode(RoundingMode.DOWN);//向下取整
        return nt.format(num);
    }

    public static void main(String[] args) {
        String s = formatPercent(new BigDecimal("0.66"));
        System.out.println(s);
    }

}
