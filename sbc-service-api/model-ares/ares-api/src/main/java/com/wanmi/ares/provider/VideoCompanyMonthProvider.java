package com.wanmi.ares.provider;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.view.wechatvideo.VideoCompanyMonthView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author zhaiqiankun
 * @className VideoCompanyMonthProvider
 * @description 视频号订单公司维度每月统计
 * @date 2022/4/8 13:33
 **/
@FeignClient(name = "${application.ares.name}", contextId = "VideoCompanyMonthProvider")
public interface VideoCompanyMonthProvider {

    /**
     * 分页查询
     * @param request
     * @return
     */
    @PostMapping("/ares/${application.ares.version}/videocompanymonth/page")
    PageInfo<VideoCompanyMonthView> getPage(@RequestBody @Valid VideoQueryPageRequest request);

}
