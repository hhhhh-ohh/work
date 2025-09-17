package com.wanmi.ares.provider;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.view.wechatvideo.VideoCompanyThirtyView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author zhaiqiankun
 * @className VideoCompanyMonthProvider
 * @description 视频号订单公司维度30天统计
 * @date 2022/4/8 13:33
 **/
@FeignClient(name = "${application.ares.name}", contextId = "VideoCompanyThirdProvider")
public interface VideoCompanyThirdProvider {

    @PostMapping("/ares/${application.ares.version}/videocompanythird/page")
    PageInfo<VideoCompanyThirtyView> getPage(@RequestBody @Valid VideoQueryPageRequest request);

}
