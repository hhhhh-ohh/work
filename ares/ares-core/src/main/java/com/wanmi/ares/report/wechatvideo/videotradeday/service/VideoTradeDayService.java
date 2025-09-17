package com.wanmi.ares.report.wechatvideo.videotradeday.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.trade.dao.TradeReportMapper;
import com.wanmi.ares.report.trade.model.request.TradeCollect;
import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoCompanyBase;
import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoTradeDay;
import com.wanmi.ares.report.wechatvideo.videotradeday.dao.VideoTradeDayMapper;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.request.wechatvideo.VideoOverviewQueryRequest;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.ares.view.wechatvideo.VideoTradeDayView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaiqiankun
 * @className VideoTradeDayService
 * @description 视频号维度每天统计方法
 * @date 2022/4/8 11:33
 **/
@Slf4j
@Service
public class VideoTradeDayService {
    @Autowired
    private VideoTradeDayMapper videoTradeDayMapper;

    @Autowired
    private TradeReportMapper tradeReportMapper;

    /**
     * 分页查询数据
     * @param request
     * @return
     */
    public PageInfo<VideoTradeDayView> getPage(VideoQueryPageRequest request) {
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<VideoTradeDayView> list = videoTradeDayMapper.getList(request);
        return new PageInfo<>(list);
    }

    /**
     * 统计数量
     * @param request
     * @return
     */
    public long count(VideoQueryPageRequest request) {
        return videoTradeDayMapper.total(request);
    }

    public WechatVideoTradeDay calcPlatformData(LocalDate date, List<WechatVideoTradeDay> videoTradeDayList) {
        //平台总计
        WechatVideoTradeDay wechatVideoTradeDay = new WechatVideoTradeDay();
        wechatVideoTradeDay.setCompanyInfoId(0L);
        wechatVideoTradeDay.setVideoSaleAmount(videoTradeDayList.stream().map(v->v.getVideoSaleAmount()).reduce(BigDecimal.ZERO,(bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2)));
        wechatVideoTradeDay.setLiveSaleAmount(videoTradeDayList.stream().map(v->v.getLiveSaleAmount()).reduce(BigDecimal.ZERO,(bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2)));
        wechatVideoTradeDay.setShopwindowSaleAmount(videoTradeDayList.stream().map(v->v.getShopwindowSaleAmount()).reduce(BigDecimal.ZERO,(bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2)));
        wechatVideoTradeDay.setVideoReturnAmount(videoTradeDayList.stream().map(v->v.getVideoReturnAmount()).reduce(BigDecimal.ZERO,(bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2)));
        wechatVideoTradeDay.setLiveReturnAmount(videoTradeDayList.stream().map(v->v.getLiveReturnAmount()).reduce(BigDecimal.ZERO,(bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2)));
        wechatVideoTradeDay.setShopwindowReturnAmount(videoTradeDayList.stream().map(v->v.getShopwindowReturnAmount()).reduce(BigDecimal.ZERO,(bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2)));
        wechatVideoTradeDay.setDate(date);
        wechatVideoTradeDay.setCreateTime(LocalDateTime.now());
        return wechatVideoTradeDay;
    }

    @Transactional(rollbackFor = Exception.class)
    public void reduceTradeBase(LocalDate date) {
        LocalDate yesterday = date.minusDays(1);
        videoTradeDayMapper.deleteByDate(yesterday);
        TradeCollect tradeCollect = TradeCollect.builder()
                .beginDate(yesterday)
                .endDate(date)
                .beginIndex(0)
                .pageSize(5000)
                .build();
        List<WechatVideoCompanyBase> wechatVideoCompanyBases;
        //平台总计
        WechatVideoTradeDay wechatVideoTradeDay = new WechatVideoTradeDay();
        wechatVideoTradeDay.setVideoSaleAmount(new BigDecimal("0"));
        wechatVideoTradeDay.setLiveSaleAmount(new BigDecimal("0"));
        wechatVideoTradeDay.setShopwindowSaleAmount(new BigDecimal("0"));
        wechatVideoTradeDay.setVideoReturnAmount(new BigDecimal("0"));
        wechatVideoTradeDay.setLiveReturnAmount(new BigDecimal("0"));
        wechatVideoTradeDay.setShopwindowReturnAmount(new BigDecimal("0"));
        wechatVideoTradeDay.setCompanyInfoId(0L);
        wechatVideoTradeDay.setDate(yesterday);
        wechatVideoTradeDay.setCreateTime(LocalDateTime.now());
        while (CollectionUtils.isNotEmpty(wechatVideoCompanyBases = tradeReportMapper.wechatTradeStatistics(tradeCollect))) {
            List<WechatVideoTradeDay> videoTradeDayList = wechatVideoCompanyBases.stream()
                    .filter(i -> !StoreSelectType.isMockCompanyInfoId(i.getCompanyInfoId()))
                    .map(i -> {
                        WechatVideoTradeDay videoTradeDay = KsBeanUtil.convert(i, WechatVideoTradeDay.class);
                        videoTradeDay.setDate(yesterday);
                        videoTradeDay.setCreateTime(LocalDateTime.now());
                        return videoTradeDay;
                    }).collect(Collectors.toList());
            videoTradeDayMapper.batchAdd(videoTradeDayList);
            wechatVideoTradeDay.setVideoSaleAmount(wechatVideoTradeDay.getVideoSaleAmount().add(videoTradeDayList.stream().map(v->v.getVideoSaleAmount()).reduce(BigDecimal.ZERO,(bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2))));
            wechatVideoTradeDay.setLiveSaleAmount(wechatVideoTradeDay.getLiveSaleAmount().add(videoTradeDayList.stream().map(v->v.getLiveSaleAmount()).reduce(BigDecimal.ZERO,(bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2))));
            wechatVideoTradeDay.setShopwindowSaleAmount(wechatVideoTradeDay.getShopwindowSaleAmount().add(videoTradeDayList.stream().map(v->v.getShopwindowSaleAmount()).reduce(BigDecimal.ZERO,(bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2))));
            wechatVideoTradeDay.setVideoReturnAmount(wechatVideoTradeDay.getVideoReturnAmount().add(videoTradeDayList.stream().map(v->v.getVideoReturnAmount()).reduce(BigDecimal.ZERO,(bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2))));
            wechatVideoTradeDay.setLiveReturnAmount(wechatVideoTradeDay.getLiveReturnAmount().add(videoTradeDayList.stream().map(v->v.getLiveReturnAmount()).reduce(BigDecimal.ZERO,(bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2))));
            wechatVideoTradeDay.setShopwindowReturnAmount(wechatVideoTradeDay.getShopwindowReturnAmount().add(videoTradeDayList.stream().map(v->v.getShopwindowReturnAmount()).reduce(BigDecimal.ZERO,(bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2))));
            tradeCollect.setBeginIndex(tradeCollect.getBeginIndex()+tradeCollect.getPageSize());
        }
        videoTradeDayMapper.batchAdd(Collections.singletonList(wechatVideoTradeDay));
    }

    /**
     * @description 汇总销售概况
     * @author malianfeng
     * @date 2022/4/29 11:30
     * @param request
     * @return com.wanmi.ares.view.wechatvideo.VideoTradeDayView
     */
    public VideoTradeDayView overview(VideoOverviewQueryRequest request) {
        // 汇总当天之前的销售数据
        VideoQueryPageRequest summaryRequest = new VideoQueryPageRequest();
        summaryRequest.setCompanyInfoId(request.getCompanyInfoId());
        summaryRequest.setEndDate(LocalDate.now());
        VideoTradeDayView overviewData = videoTradeDayMapper.summary(summaryRequest);
        // 实时查询当天的销售数据
        boolean isPlatform = request.getCompanyInfoId() == 0L;
        LocalDate currentDate = LocalDate.now();
        TradeCollect tradeCollect = TradeCollect.builder()
                // 平台端查询全部商家，CompanyInfoId置空，商家端仅查询自身数据
                .companyId(isPlatform ? null : String.valueOf(request.getCompanyInfoId()))
                .beginDate(currentDate)
                .endDate(currentDate.plusDays(1)).build();
        List<WechatVideoCompanyBase> wechatVideoCompanyBases = tradeReportMapper.wechatTradeStatistics(tradeCollect);
        List<WechatVideoTradeDay> videoTradeDayList = wechatVideoCompanyBases.stream()
                .filter(i -> !StoreSelectType.isMockCompanyInfoId(i.getCompanyInfoId()))
                .map(i -> KsBeanUtil.convert(i, WechatVideoTradeDay.class))
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(videoTradeDayList)) {
            // 实时数据
            WechatVideoTradeDay nowData;
            if (isPlatform) {
                // 平台端，需要将所有商家做一次汇总
                nowData = calcPlatformData(currentDate, videoTradeDayList);
            } else {
                // 商家端，取首元素
                nowData = videoTradeDayList.get(0);
            }
            // 将天表数据和实时数据累加返回
            overviewData.setVideoSaleAmount(overviewData.getVideoSaleAmount().add(nowData.getVideoSaleAmount()));
            overviewData.setLiveSaleAmount(overviewData.getLiveSaleAmount().add(nowData.getLiveSaleAmount()));
            overviewData.setShopwindowSaleAmount(overviewData.getShopwindowSaleAmount().add(nowData.getShopwindowSaleAmount()));
            overviewData.setVideoReturnAmount(overviewData.getVideoReturnAmount().add(nowData.getVideoReturnAmount()));
            overviewData.setLiveReturnAmount(overviewData.getLiveReturnAmount().add(nowData.getLiveReturnAmount()));
            overviewData.setShopwindowReturnAmount(overviewData.getShopwindowReturnAmount().add(nowData.getShopwindowReturnAmount()));
        }
        return overviewData;
    }
}
