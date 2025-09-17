package com.wanmi.ares.marketing.coupon.service;

import com.wanmi.ares.common.DataPeriod;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.marketing.coupon.dao.CouponOverViewMapper;
import com.wanmi.ares.marketing.coupon.dao.CouponOverviewDayMapper;
import com.wanmi.ares.marketing.coupon.dao.CouponOverviewRecentMapper;
import com.wanmi.ares.marketing.coupon.dao.CouponOverviewWeekMapper;
import com.wanmi.ares.request.coupon.CouponEffectRequest;
import com.wanmi.ares.request.coupon.CouponQueryRequest;
import com.wanmi.ares.utils.DateUtil;
import com.wanmi.ares.view.coupon.CouponOverviewView;
import com.wanmi.ares.view.coupon.DataPeriodView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @ClassName CouponScheduled
 * @Description
 * @Author zhanggaolei
 * @Date 2021/1/11 9:36
 * @Version 1.0
 **/
@Service
public class CouponOverviewService {

    @Autowired
    private CouponOverViewMapper couponOverViewMapper;
    @Autowired
    private CouponOverviewRecentMapper recentMapper;
    @Autowired
    private CouponOverviewDayMapper dayMapper;
    @Autowired
    private CouponOverviewWeekMapper weekMapper;

    @Autowired
    private CouponEffectService couponEffectService;

    public void generator() {
        LocalDate localDate = LocalDate.now().minusDays(1);
        dayStatistics(localDate);
        weekStatistics(localDate);
        recentStatistics(localDate);
    }

    //插入天数据
    public void dayStatistics(LocalDate localDate) {
        String endTime = DateUtil.format(localDate.plusDays(1), DateUtil.FMT_DATE_1);
        String beginTime = DateUtil.format(localDate, DateUtil.FMT_DATE_1);
        String statDate = beginTime;

        couponOverViewMapper.deleteDay(statDate);
        couponOverViewMapper.saveBossDay(statDate, beginTime, endTime);
        couponOverViewMapper.saveSupplierDay(statDate, beginTime, endTime);
    }
    //插入周数据
    public void weekStatistics(LocalDate localDate) {

        String endTime = DateUtil.format(localDate.plusDays(1), DateUtil.FMT_DATE_1);
        String statDate = DateUtil.format(localDate, DateUtil.FMT_DATE_1);
        LocalDate sunday = DateUtil.getThisWeekSundayDate(localDate);
        if(sunday.compareTo(LocalDate.now())<0){
            statDate = sunday.toString();
            endTime = sunday.plusDays(1).toString();
        }

        String beginTime = DateUtil.getThisWeekMonday(localDate);
        couponOverViewMapper.deleteWeek(beginTime);
        couponOverViewMapper.saveBossWeek(statDate, beginTime, endTime);
        couponOverViewMapper.saveSupplierWeek(statDate, beginTime, endTime);
    }

    //插入最近*天数据
    public void recentStatistics(LocalDate localDate){
        String endTime = DateUtil.format(localDate.plusDays(1), DateUtil.FMT_DATE_1);
        String beginTime = DataPeriod.getStartDate();
        couponOverViewMapper.deleteRecent();
        couponOverViewMapper.saveBossRecent(beginTime,endTime);
        couponOverViewMapper.saveSupplierRecent(beginTime,endTime);
    }

  /*  public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        // 2014-12-26
        calendar.set(2020, Calendar.DECEMBER, 30);
        Date strDate1 = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long time = 1609387861000L;
        Date date = new Date(time);


        System.out.println(simpleDateFormat.format(date));
        System.out.println(simpleDateFormat.format(strDate1));

        LocalDate localDate = LocalDate.now();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        System.out.println(dayOfWeek.getValue());
    }*/

    public CouponOverviewView overview(CouponQueryRequest request){
        CouponOverviewView couponOverviewView = recentMapper.selectByStoreId(request.getStoreId());
        if(couponOverviewView == null){
            couponOverviewView = CouponOverviewView.builder()
                    .storeId(request.getStoreId())
                    .acquireCount(0L)
                    .acquireCustomerCount(0L)
                    .useCount(0L)
                    .useCustomerCount(0L)
                    .payMoney(new BigDecimal(0))
                    .discountMoney(new BigDecimal(0))
                    .payTradeCount(0L)
                    .payGoodsCount(0L)
                    .oldCustomerCount(0L)
                    .newCustomerCount(0L)
                    .payCustomerCount(0L)
                    .build();

        }
        return couponOverviewView;
    }

    public List<CouponOverviewView> overviewList(CouponQueryRequest request){
        CouponEffectRequest couponEffectRequest = new CouponEffectRequest();
        couponEffectRequest.setStoreId(request.getStoreId());
        if (StoreSelectType.O2O == request.getStoreSelectType()) {
            couponEffectRequest.setStoreSelectType(StoreSelectType.O2O);
        }
        long total = couponEffectService.countByPageTotal(couponEffectRequest);
        //没有优惠券，返回空数据给前端
        if (total == 0) {
            return Collections.emptyList();
        }

        List<CouponOverviewView> retList = new ArrayList<>();
        switch (request.getTrendType()){
            case DAY:
                retList.addAll(dayMapper.selectByStoreId(request.getStoreId(),DataPeriod.getStartDate()));
                break;
            case WEEK:
                retList.addAll(weekMapper.selectByStoreId(request.getStoreId(),DataPeriod.getStartDate()));
                break;
            default:

        }
        return retList;
    }


    public DataPeriodView getDataPeriod(){
        DataPeriodView dataPeriodView = new DataPeriodView();
        StringBuilder stringBuilder = new StringBuilder(DataPeriod.getDateRange())
                .append("（支持最近")
                .append(DataPeriod.period)
                .append("天的营销数据统计）");
        dataPeriodView.setDetail(stringBuilder.toString());
        dataPeriodView.setStartDate(DataPeriod.getStartDate());
        dataPeriodView.setEndDate(LocalDate.now().minusDays(1).toString());
        dataPeriodView.setPeriod(DataPeriod.period);
        return dataPeriodView;
    }
}
