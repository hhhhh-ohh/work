package com.wanmi.ares.provider.impl;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.provider.VideoCompanyThirdProvider;
import com.wanmi.ares.report.wechatvideo.videocompanythirty.service.VideoCompanyThirtyService;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.view.wechatvideo.VideoCompanyThirtyView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaiqiankun
 * @className VideoCompanyMonthController
 * @description 公司维度每30天统计实现接口
 * @date 2022/4/8 17:02
 **/
@RestController
public class VideoCompanyThirdController implements VideoCompanyThirdProvider {
    @Autowired
    private VideoCompanyThirtyService videoCompanyService;

    @Override
    public PageInfo<VideoCompanyThirtyView> getPage(VideoQueryPageRequest request) {
        return videoCompanyService.getPage(request);
    }

}
