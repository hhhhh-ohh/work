package com.wanmi.ares.provider.impl;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.provider.VideoCompanyMonthProvider;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.report.wechatvideo.videocompanymonth.service.VideoCompanyMonthService;
import com.wanmi.ares.view.wechatvideo.VideoCompanyMonthView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaiqiankun
 * @className VideoCompanyMonthController
 * @description 视频号订单公司维度月统计实现接口
 * @date 2022/4/8 17:02
 **/
@RestController
public class VideoCompanyMonthController implements VideoCompanyMonthProvider {
    @Autowired
    private VideoCompanyMonthService videoCompanyMonthService;
    @Override
    public PageInfo<VideoCompanyMonthView> getPage(VideoQueryPageRequest request) {
        return videoCompanyMonthService.getPage(request);
    }

}
