package com.wanmi.ares.report.wechatvideo.videocompanyseven.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.report.trade.dao.TradeReportMapper;
import com.wanmi.ares.report.trade.model.request.TradeCollect;
import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoCompanyBase;
import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoCompanySeven;
import com.wanmi.ares.report.wechatvideo.videocompanyseven.dao.VideoCompanySevenMapper;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.utils.KsBeanUtil;
import com.wanmi.ares.view.wechatvideo.VideoCompanySevenView;
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
 * @className VideoCompanySevenService
 * @description 视频号订单公司维度7天统计方法
 * @date 2022/4/8 11:33
 **/
@Slf4j
@Service
public class VideoCompanySevenService {
    @Autowired
    private VideoCompanySevenMapper videoMapper;

    @Autowired
    private TradeReportMapper tradeReportMapper;

    /**
     * 分页查询数据
     * @param request
     * @return
     */
    public PageInfo<VideoCompanySevenView> getPage(VideoQueryPageRequest request) {
        PageHelper.startPage(request.getPageNum() + 1, request.getPageSize());
        List<VideoCompanySevenView> list = videoMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public void reduceTradeBase(LocalDate date) {
        videoMapper.deleteByDate(date);
        TradeCollect tradeCollect = TradeCollect.builder()
                .beginDate(date.minusDays(7))
                .beginIndex(0)
                .pageSize(5000)
                .endDate(date)
                .build();
        List<WechatVideoCompanyBase> wechatVideoCompanyBases;
        while (CollectionUtils.isNotEmpty(wechatVideoCompanyBases = tradeReportMapper.wechatTradeStatistics(tradeCollect))) {
            List<WechatVideoCompanySeven> videoTradeDayList = wechatVideoCompanyBases.stream()
                    .filter(i -> !StoreSelectType.isMockCompanyInfoId(i.getCompanyInfoId()))
                    .map(i -> {
                        WechatVideoCompanySeven wechatVideoCompanySeven = KsBeanUtil.convert(i, WechatVideoCompanySeven.class);
                        wechatVideoCompanySeven.setDate(date);
                        wechatVideoCompanySeven.setCreateTime(LocalDateTime.now());
                        return wechatVideoCompanySeven;
                    }).collect(Collectors.toList());
            videoMapper.batchAdd(videoTradeDayList);
            tradeCollect.setBeginIndex(tradeCollect.getBeginIndex()+tradeCollect.getPageSize());
        }
    }
}
