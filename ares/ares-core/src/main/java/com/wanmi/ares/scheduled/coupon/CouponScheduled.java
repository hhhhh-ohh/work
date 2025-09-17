package com.wanmi.ares.scheduled.coupon;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.TrendType;
import com.wanmi.ares.marketing.coupon.service.CouponEffectService;
import com.wanmi.ares.marketing.coupon.service.CouponOverviewService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @ClassName CouponScheduled
 * @Description
 * @Author zhanggaolei
 * @Date 2021/1/14 9:36
 * @Version 1.0
 **/
@Slf4j
@Component
public class CouponScheduled {
    @Autowired
    private CouponOverviewService couponOverviewService;

    @Autowired
    private CouponEffectService couponEffectService;


    @XxlJob(value = "couponScheduled")
    public void execute() throws Exception {
        String s = XxlJobHelper.getJobParam();
        CouponStatistics statistics ;
        if(StringUtils.isNotEmpty(s)){
            statistics = JSONObject.parseObject(s,CouponStatistics.class);
            if(statistics!=null && statistics.getStatDate() != null){
                if(statistics.getTrendType()!= null){
                    switch (statistics.getTrendType()){
                        case DAY:
                            couponOverviewService.dayStatistics(statistics.getStatDate());
                            return;
                        case WEEK:
                            couponOverviewService.weekStatistics(statistics.getStatDate());
                            return;
                        default:

                    }
                }
            }
        }
        log.info("开始统计概况和趋势数据");
        couponOverviewService.generator();
        log.info("概况和趋势数据统计完成");

        log.info("开始统计活动分析数据");
        couponEffectService.generator();
        log.info("活动分析数据统计成功");
    }

    @Data
    public static class CouponStatistics{
        private TrendType trendType;
        //统计时间，如果是日，则重新统计当天数据，如果是周——则统计该日期所在的周数据
        private LocalDate statDate;
    }
}
