package com.wanmi.ares;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.marketing.coupon.service.CouponEffectExportService;
import com.wanmi.ares.marketing.coupon.service.CouponEffectService;
import com.wanmi.ares.marketing.coupon.service.CouponOverviewService;
import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.scheduled.coupon.CouponScheduled;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName TestController
 * @description
 * @Author zhanggaolei
 * @Date 2021/1/14 10:03
 * @Version 1.0
 **/
@Slf4j
@RestController
public class TestController {
    @Autowired
    private CouponOverviewService couponOverviewService;
    @Autowired
    private CouponEffectService couponEffectService;
    @Autowired
    private CouponEffectExportService effectExportService;

    @GetMapping("/test/job/coupon-overview")
    public String couponOverviewGenerator(){
        couponOverviewService.generator();
        return CommonErrorCodeEnum.K000000.getMsg();
    }

    @GetMapping("/test/job/coupon-effect")
    public String couponEffectGenerator(){
        couponEffectService.generator();
        return CommonErrorCodeEnum.K000000.getMsg();
    }

    @PostMapping("/test/job/coupon-overview-history")
    public String couponEffectGenerator(@RequestBody CouponScheduled.CouponStatistics statistics){
        if(statistics!=null && statistics.getStatDate() != null) {
            if (statistics.getTrendType() != null) {
                switch (statistics.getTrendType()) {
                    case DAY:
                        couponOverviewService.dayStatistics(statistics.getStatDate());
                        return CommonErrorCodeEnum.K000000.getMsg();
                    case WEEK:
                        couponOverviewService.weekStatistics(statistics.getStatDate());
                        return CommonErrorCodeEnum.K000000.getMsg();
                    default:
                }
            }
        }
        log.info("开始统计概况和趋势数据");
        couponOverviewService.generator();
        log.info("概况和趋势数据统计完成");

        log.info("开始统计活动分析数据");
        couponEffectService.generator();
        log.info("活动分析数据统计成功");

        return CommonErrorCodeEnum.K000000.getMsg();
    }

    @SneakyThrows
    @PostMapping("/test/job/coupon-inf-effect-export")
    public List<String> couponInfoEffectExport(@RequestBody ExportQuery query){
       return effectExportService.exportCouponInfoEffect(query);
    }


}
