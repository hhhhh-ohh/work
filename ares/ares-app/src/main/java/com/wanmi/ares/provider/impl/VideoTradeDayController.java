package com.wanmi.ares.provider.impl;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.provider.VideoTradeDayProvider;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.report.wechatvideo.videotradeday.service.VideoTradeDayService;
import com.wanmi.ares.request.wechatvideo.VideoOverviewQueryRequest;
import com.wanmi.ares.view.wechatvideo.VideoTradeDayView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author zhaiqiankun
 * @className VideoTradeDayController
 * @description 视频号维度每天统计实现接口
 * @date 2022/4/8 17:02
 **/
@RestController
public class VideoTradeDayController implements VideoTradeDayProvider {

    @Autowired
    private VideoTradeDayService videoService;

    @Override
    public PageInfo<VideoTradeDayView> getPage(VideoQueryPageRequest request) {
        return videoService.getPage(request);
    }

    @Override
    public VideoTradeDayView overview(@Valid VideoOverviewQueryRequest request) {
        return videoService.overview(request);
    }

}
