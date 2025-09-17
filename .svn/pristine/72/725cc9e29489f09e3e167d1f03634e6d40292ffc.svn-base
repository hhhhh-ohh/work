package com.wanmi.ares.report.wechatvideo.service;

import com.wanmi.ares.report.trade.dao.TradeDayMapper;
import com.wanmi.ares.report.wechatvideo.videocompanymonth.service.VideoCompanyMonthService;
import com.wanmi.ares.report.wechatvideo.videocompanyseven.service.VideoCompanySevenService;
import com.wanmi.ares.report.wechatvideo.videocompanythirty.service.VideoCompanyThirtyService;
import com.wanmi.ares.report.wechatvideo.videomonth.service.VideoMonthService;
import com.wanmi.ares.report.wechatvideo.videoseven.service.VideoSevenService;
import com.wanmi.ares.report.wechatvideo.videothird.service.VideoThirtyService;
import com.wanmi.ares.report.wechatvideo.videotradeday.service.VideoTradeDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WechatVideoReportService {


    @Autowired
    private VideoTradeDayService videoTradeDayService;
    @Autowired
    private VideoSevenService videoSevenService;

    @Autowired
    private VideoThirtyService videoThirtyService;

    @Autowired
    private VideoMonthService videoMonthService;

    @Autowired
    private TradeDayMapper tradeDayMapper;

    @Autowired
    private VideoCompanySevenService videoCompanySevenService;
    @Autowired
    private VideoCompanyThirtyService videoCompanyThirtyService;
    @Autowired
    private VideoCompanyMonthService videoCompanyMonthService;


    public void generateData(String param) {
        LocalDate now = LocalDate.now();
        if (param.contains("1")) {
            yesterday(now);
            seven(now);
            thirty(now);
        }
        if (param.contains("2")) {
            month(now);
        }
    }


    public void yesterday(LocalDate date) {
        videoTradeDayService.reduceTradeBase(date);
    }


    public void seven(LocalDate date) {
        videoSevenService.reduceTradeBase(date);
        videoCompanySevenService.reduceTradeBase(date);
    }

    public void thirty(LocalDate date) {
        videoThirtyService.reduceTradeBase(date);
        videoCompanyThirtyService.reduceTradeBase(date);
    }

    public void month(LocalDate date) {
            videoMonthService.reduceTradeBase(date);
            videoCompanyMonthService.reduceTradeBase(date);
    }
}
