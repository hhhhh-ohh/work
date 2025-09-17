package com.wanmi.ares.report.wechatvideo.videoseven.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.trade.dao.TradeReportMapper;
import com.wanmi.ares.report.trade.model.request.TradeCollect;
import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoBase;
import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoSeven;
import com.wanmi.ares.report.wechatvideo.videoseven.dao.VideoSevenMapper;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.ares.view.wechatvideo.VideoSevenView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaiqiankun
 * @className VideoSevenService
 * @description 视频号维度最近7天统计方法
 * @date 2022/4/8 11:33
 **/
@Slf4j
@Service
public class VideoSevenService {
    @Autowired
    private VideoSevenMapper videoMapper;

    @Autowired
    private TradeReportMapper tradeReportMapper;

    /**
     * 分页查询数据
     * @param request
     * @return
     */
    public PageInfo<VideoSevenView> getPage(VideoQueryPageRequest request) {
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<VideoSevenView> list = videoMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public void reduceTradeBase(LocalDate date) {
        videoMapper.deleteByDate(date);
        TradeCollect tradeCollect = TradeCollect.builder()
                .beginDate(date.minusDays(7))
                .endDate(date)
                .beginIndex(0)
                .pageSize(5000)
                .build();
        List<WechatVideoBase> wechatVideoBases;
        while (CollectionUtils.isNotEmpty(wechatVideoBases = tradeReportMapper.wechatVideotatistics(tradeCollect))) {
                List<WechatVideoSeven> videoTradeDayList = wechatVideoBases.stream()
                        .filter(i -> !StoreSelectType.isMockCompanyInfoId(i.getCompanyInfoId()))
                        .map(i -> {
                            WechatVideoSeven wechatVideoSeven = KsBeanUtil.convert(i, WechatVideoSeven.class);
                            wechatVideoSeven.setDate(date);
                            wechatVideoSeven.setCreateTime(LocalDateTime.now());
                            return wechatVideoSeven;
                        }).collect(Collectors.toList());
                videoMapper.batchAdd(videoTradeDayList);
                tradeCollect.setBeginIndex(tradeCollect.getBeginIndex()+tradeCollect.getPageSize());
        }
        tradeCollect.setBeginIndex(null);
        tradeCollect.setPageSize(null);
        List<WechatVideoBase> wechatVideoBaseList = tradeReportMapper.wechatVideotatisticsForBoss(tradeCollect);
        if (CollectionUtils.isNotEmpty(wechatVideoBaseList)) {
            List<WechatVideoSeven> videoTradeDayList = wechatVideoBaseList.stream()
                    .map(i -> {
                        WechatVideoSeven wechatVideoSeven = KsBeanUtil.convert(i, WechatVideoSeven.class);
                        wechatVideoSeven.setDate(date);
                        wechatVideoSeven.setCreateTime(LocalDateTime.now());
                        return wechatVideoSeven;
                    }).collect(Collectors.toList());
            videoMapper.batchAdd(videoTradeDayList);
        }
    }
}
