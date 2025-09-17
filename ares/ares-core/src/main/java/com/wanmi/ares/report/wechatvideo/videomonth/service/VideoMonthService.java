package com.wanmi.ares.report.wechatvideo.videomonth.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.trade.dao.TradeReportMapper;
import com.wanmi.ares.report.trade.model.request.TradeCollect;
import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoBase;
import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoMonth;
import com.wanmi.ares.report.wechatvideo.videomonth.dao.VideoMonthMapper;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.ares.view.wechatvideo.VideoMonthView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaiqiankun
 * @className VideoMonthService
 * @description 视频号订单公司维度每月统计方法
 * @date 2022/4/8 11:33
 **/
@Slf4j
@Service
public class VideoMonthService {
    @Autowired
    private VideoMonthMapper videoMapper;

    @Autowired
    private TradeReportMapper tradeReportMapper;

    /**
     * 分页查询数据
     * @param request
     * @return
     */
    public PageInfo<VideoMonthView> getPage(VideoQueryPageRequest request) {
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<VideoMonthView> list = videoMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public void reduceTradeBase(LocalDate now) {
        LocalDate range = now.minusMonths(1);
        LocalDate begain = range.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = range.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);
        videoMapper.deleteByDate(range.getYear(),range.getMonthValue());
        TradeCollect tradeCollect = TradeCollect.builder()
                .beginDate(begain)
                .beginIndex(0)
                .pageSize(5000)
                .endDate(end)
                .build();
        List<WechatVideoBase> wechatVideoCompanyBases;
        while (CollectionUtils.isNotEmpty(wechatVideoCompanyBases = tradeReportMapper.wechatVideotatistics(tradeCollect))) {
                List<WechatVideoMonth> wechatVideoMonths = wechatVideoCompanyBases.stream()
                        .filter(i -> !StoreSelectType.isMockCompanyInfoId(i.getCompanyInfoId()))
                        .map(i -> {
                            WechatVideoMonth videoCompanyMonth = KsBeanUtil.convert(i, WechatVideoMonth.class);
                            videoCompanyMonth.setYear(range.getYear());
                            videoCompanyMonth.setMonth(range.getMonthValue());
                            videoCompanyMonth.setCreateTime(LocalDateTime.now());
                            return videoCompanyMonth;
                        }).collect(Collectors.toList());
                videoMapper.batchAdd(wechatVideoMonths);
            tradeCollect.setBeginIndex(tradeCollect.getBeginIndex()+tradeCollect.getPageSize());
        }
        tradeCollect.setBeginIndex(null);
        tradeCollect.setPageSize(null);
        List<WechatVideoBase> wechatVideoBases = tradeReportMapper.wechatVideotatisticsForBoss(tradeCollect);
        if (CollectionUtils.isNotEmpty(wechatVideoBases)) {
            List<WechatVideoMonth> wechatVideoMonths = wechatVideoBases.stream()
                    .map(i -> {
                        WechatVideoMonth videoCompanyMonth = KsBeanUtil.convert(i, WechatVideoMonth.class);
                        videoCompanyMonth.setYear(range.getYear());
                        videoCompanyMonth.setMonth(range.getMonthValue());
                        videoCompanyMonth.setCreateTime(LocalDateTime.now());
                        return videoCompanyMonth;
                    }).collect(Collectors.toList());
            videoMapper.batchAdd(wechatVideoMonths);
        }
    }
}
