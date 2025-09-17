package com.wanmi.ares.provider.impl;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.provider.VideoMonthProvider;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.report.wechatvideo.videomonth.service.VideoMonthService;
import com.wanmi.ares.view.wechatvideo.VideoMonthView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaiqiankun
 * @className VideoCompanyMonthController
 * @description 视频号订单维度月统计实现接口
 * @date 2022/4/8 17:02
 **/
@RestController
public class VideoMonthController implements VideoMonthProvider {
    @Autowired
    private VideoMonthService videoService;
    @Override
    public PageInfo<VideoMonthView> getPage(VideoQueryPageRequest request) {
        return videoService.getPage(request);
    }

}
