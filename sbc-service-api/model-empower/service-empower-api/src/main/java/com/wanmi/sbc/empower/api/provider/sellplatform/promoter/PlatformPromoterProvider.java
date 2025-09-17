package com.wanmi.sbc.empower.api.provider.sellplatform.promoter;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sellplatform.promoter.PlatformPromoterListRequest;
import com.wanmi.sbc.empower.api.response.sellplatform.promoter.PlatformPromoterListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;


/**
 * @author wur
 * @className PlatformPromoterProvider
 * @description 微信视频推广员
 * @date 2022/4/13 16:28
 */
@FeignClient(value = "${application.empower.name}", contextId = "PlatformPromoterProvider")
public interface PlatformPromoterProvider {

    /**
     * @description  查询视频号推广员列表
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
    @PostMapping("/empower/${application.empower.version}/platform/promoter_list/")
    BaseResponse<PlatformPromoterListResponse> getPromoterList(@RequestBody @Valid PlatformPromoterListRequest request);

}
