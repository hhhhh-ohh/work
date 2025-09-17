package com.wanmi.sbc.empower.provider.impl.sellplatform.apply;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.sellplatform.apply.PlatformApplyProvider;
import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.apply.PlatformApplySceneRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.apply.PlatformFinishAccessRequest;
import com.wanmi.sbc.empower.api.response.sellplatform.apply.PlatformCheckResponse;
import com.wanmi.sbc.empower.sellplatform.PlatformContext;
import com.wanmi.sbc.empower.sellplatform.PlatformRegistApplyService;
import com.wanmi.sbc.empower.sellplatform.PlatformServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
*
 * @description
 * @author  wur
 * @date: 2022/4/11 10:41
 **/
@RestController
public class PlatformApplyController implements PlatformApplyProvider {

    @Autowired private PlatformContext thirdPlatformContext;


    @Override
    public BaseResponse<PlatformCheckResponse> registerCheck(ThirdBaseRequest request) {
        PlatformRegistApplyService applyService = thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.REGIST_APPLY_SERVICE);
        return applyService.check();
    }

    @Override
    public BaseResponse registerApply(ThirdBaseRequest request) {
        PlatformRegistApplyService applyService = thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.REGIST_APPLY_SERVICE);
        return applyService.apply();
    }

    @Override
    public BaseResponse registerFinishAccess(PlatformFinishAccessRequest request) {
        PlatformRegistApplyService applyService = thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.REGIST_APPLY_SERVICE);
        return applyService.finishAccess(request);
    }

    @Override
    public BaseResponse registerApplyScene(PlatformApplySceneRequest request) {
        PlatformRegistApplyService applyService = thirdPlatformContext.getPlatformService(request.getSellPlatformType(), PlatformServiceType.REGIST_APPLY_SERVICE);
        return applyService.applyScene(request);
    }
}
