package com.wanmi.sbc.vas.api.provider.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.sellplatform.promoter.SellPlatformPromoterListRequest;
import com.wanmi.sbc.vas.api.response.sellplatform.promoter.SellPlatformPromoterListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;


/**
 * @author wur
 * @className SellPlatformPromoterProvider
 * @description 微信视频推广员处理
 * @date 2022/4/11 10:28
 */
@FeignClient(value = "${application.vas.name}", contextId = "SellPlatformPromoterProvider")
public interface SellPlatformPromoterProvider {

    /**
     * @description  查询视频号推广员列表
     * @author  wur
     * @date: 2022/4/11 17:14
     * @param request
     **/
        @PostMapping("/vas/${application.vas.version}/sell-platform/promoter_list/")
    BaseResponse<SellPlatformPromoterListResponse> getPromoterList(@RequestBody @Valid SellPlatformPromoterListRequest request);
}
