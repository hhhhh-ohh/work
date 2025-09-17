package com.wanmi.ares.provider;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.view.wechatvideo.VideoMonthView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author zhaiqiankun
 * @className VideoCompanyMonthProvider
 * @description 视频号维度每月统计
 * @date 2022/4/8 13:33
 **/
@FeignClient(name = "${application.ares.name}", contextId = "VideoCompanyProvider")
public interface VideoMonthProvider {

    /**
     * 分页查询
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/videomonth/page")
    PageInfo<VideoMonthView> getPage(@RequestBody @Valid VideoQueryPageRequest request);

}
