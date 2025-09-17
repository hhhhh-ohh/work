package com.wanmi.ares.provider.impl;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.provider.VideoThirtyProvider;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.report.wechatvideo.videothird.service.VideoThirtyService;
import com.wanmi.ares.view.wechatvideo.VideoThirtyView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaiqiankun
 * @className VideoThirtyController
 * @description 视频号维度每30天统计实现接口
 * @date 2022/4/8 17:02
 **/
@RestController
public class VideoThirtyController implements VideoThirtyProvider {

    @Autowired
    private VideoThirtyService videoService;

    @Override
    public PageInfo<VideoThirtyView> getPage(VideoQueryPageRequest request) {
        return videoService.getPage(request);
    }

}
