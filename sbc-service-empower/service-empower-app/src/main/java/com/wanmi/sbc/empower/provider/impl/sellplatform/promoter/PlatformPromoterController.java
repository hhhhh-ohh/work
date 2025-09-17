package com.wanmi.sbc.empower.provider.impl.sellplatform.promoter;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.empower.api.provider.sellplatform.promoter.PlatformPromoterProvider;
import com.wanmi.sbc.empower.api.request.sellplatform.promoter.PlatformPromoterListRequest;
import com.wanmi.sbc.empower.api.response.sellplatform.promoter.PlatformPromoterListResponse;
import com.wanmi.sbc.empower.sellplatform.PlatformContext;
import com.wanmi.sbc.empower.sellplatform.PlatformPromoterService;
import com.wanmi.sbc.empower.sellplatform.PlatformServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author wur
 * @className ThirdPlatformPromoterController
 * @description 微信视频推广员
 * @date 2022/4/12 18:05
 **/
@RestController
public class PlatformPromoterController implements PlatformPromoterProvider {

    @Autowired private PlatformContext thirdPlatformContext;

    @Override
    public BaseResponse<PlatformPromoterListResponse> getPromoterList(@Valid PlatformPromoterListRequest request) {
        PlatformPromoterService promoterService  =
                thirdPlatformContext.getPlatformService(SellPlatformType.WECHAT_VIDEO, PlatformServiceType.PROMOTER_SERVICE);
        return promoterService.getPromoterList(request);
    }
}
