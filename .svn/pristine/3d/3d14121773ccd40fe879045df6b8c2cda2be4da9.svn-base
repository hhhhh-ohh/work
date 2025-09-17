package com.wanmi.ares.provider.impl;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.provider.VideoSevenProvider;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.report.wechatvideo.videoseven.service.VideoSevenService;
import com.wanmi.ares.view.wechatvideo.VideoSevenView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaiqiankun
 * @className VideoCompanyMonthController
 * @description 视频号维度每7天统计实现接口
 * @date 2022/4/8 17:02
 **/
@RestController
public class VideoSevenController implements VideoSevenProvider {

    @Autowired
    private VideoSevenService videoService;

    @Override
    public PageInfo<VideoSevenView> getPage(VideoQueryPageRequest request) {
        return videoService.getPage(request);
    }

}
