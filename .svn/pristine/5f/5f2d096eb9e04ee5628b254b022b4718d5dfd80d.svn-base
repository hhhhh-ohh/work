package com.wanmi.ares.provider.impl;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.provider.VideoCompanySevenProvider;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.report.wechatvideo.videocompanyseven.service.VideoCompanySevenService;
import com.wanmi.ares.view.wechatvideo.VideoCompanySevenView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaiqiankun
 * @className VideoCompanyMonthController
 * @description 视频号订单公司维度7天统计实现接口
 * @date 2022/4/8 17:02
 **/
@RestController
public class VideoCompanySevenController implements VideoCompanySevenProvider {
    @Autowired
    private VideoCompanySevenService videoCompanySevenService;

    @Override
    public PageInfo<VideoCompanySevenView> getPage(VideoQueryPageRequest request) {
        return videoCompanySevenService.getPage(request);
    }

}
