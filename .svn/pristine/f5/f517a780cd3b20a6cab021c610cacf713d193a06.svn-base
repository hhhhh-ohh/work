package com.wanmi.ares.report.wechatvideo.videothird.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.trade.dao.TradeReportMapper;
import com.wanmi.ares.report.trade.model.request.TradeCollect;
import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoBase;
import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoThirty;
import com.wanmi.ares.report.wechatvideo.videothird.dao.VideoThirtyMapper;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.ares.view.wechatvideo.VideoThirtyView;
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
 * @className VideoThirtyService
 * @description 视频号维度每30天统计方法
 * @date 2022/4/8 11:33
 **/
@Slf4j
@Service
public class VideoThirtyService {
    @Autowired
    private VideoThirtyMapper videoMapper;

    @Autowired
    private TradeReportMapper tradeReportMapper;

    /**
     * 分页查询数据
     * @param request
     * @return
     */
    public PageInfo<VideoThirtyView> getPage(VideoQueryPageRequest request) {
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<VideoThirtyView> list = videoMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public void reduceTradeBase(LocalDate date) {
        videoMapper.deleteByDate(date);
        TradeCollect tradeCollect = TradeCollect.builder()
                .beginDate(date.minusDays(30))
                .endDate(date)
                .beginIndex(0)
                .pageSize(5000)
                .build();
        List<WechatVideoBase> wechatVideoBases;
        while (CollectionUtils.isNotEmpty(wechatVideoBases = tradeReportMapper.wechatVideotatistics(tradeCollect))) {
                List<WechatVideoThirty> videoTradeDayList = wechatVideoBases.stream()
                        .filter(i -> !StoreSelectType.isMockCompanyInfoId(i.getCompanyInfoId()))
                        .map(i -> {
                            WechatVideoThirty wechatVideoThirty = KsBeanUtil.convert(i, WechatVideoThirty.class);
                            wechatVideoThirty.setDate(date);
                            wechatVideoThirty.setCreateTime(LocalDateTime.now());
                            return wechatVideoThirty;
                        }).collect(Collectors.toList());
                videoMapper.batchAdd(videoTradeDayList);
            tradeCollect.setBeginIndex(tradeCollect.getBeginIndex()+tradeCollect.getPageSize());
        }
        tradeCollect.setBeginIndex(null);
        tradeCollect.setPageSize(null);
        List<WechatVideoBase> wechatVideoBaseList = tradeReportMapper.wechatVideotatisticsForBoss(tradeCollect);
        if (CollectionUtils.isNotEmpty(wechatVideoBaseList)) {
            List<WechatVideoThirty> videoTradeDayList = wechatVideoBaseList.stream()
                    .map(i -> {
                        WechatVideoThirty wechatVideoThirty = KsBeanUtil.convert(i, WechatVideoThirty.class);
                        wechatVideoThirty.setDate(date);
                        wechatVideoThirty.setCreateTime(LocalDateTime.now());
                        return wechatVideoThirty;
                    }).collect(Collectors.toList());
            videoMapper.batchAdd(videoTradeDayList);
        }
    }
}
