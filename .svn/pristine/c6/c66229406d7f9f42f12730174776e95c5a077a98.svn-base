package com.wanmi.ares.provider;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.request.wechatvideo.VideoOverviewQueryRequest;
import com.wanmi.ares.view.wechatvideo.VideoTradeDayView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author zhaiqiankun
 * @className VideoCompanyMonthProvider
 * @description 视频号维度每天统计
 * @date 2022/4/8 13:33
 **/
@FeignClient(name = "${application.ares.name}", contextId = "VideoTradeDayProvider")
public interface VideoTradeDayProvider {

    /**
     * 分页查询
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/videotradeday/page")
    PageInfo<VideoTradeDayView> getPage(@RequestBody @Valid VideoQueryPageRequest request);



    /**
     * 汇总销售概况
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/videotradeday/overview")
    VideoTradeDayView overview(@RequestBody @Valid VideoOverviewQueryRequest request);

}
