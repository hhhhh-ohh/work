package com.wanmi.sbc.vas.provider.impl.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.response.sellplatform.apply.PlatformCheckResponse;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformApplyProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.apply.SellPlatformApplySceneRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.apply.SellPlatformFinishAccessRequest;
import com.wanmi.sbc.vas.sellplatform.SellPlatformApplyService;
import com.wanmi.sbc.vas.sellplatform.SellPlatformContext;
import com.wanmi.sbc.vas.sellplatform.SellPlatformServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * @description 代销申请处理
 * @author malianfeng
 * @date 2022/4/22 20:00
 */
@RestController
public class SellPlatformApplyController implements SellPlatformApplyProvider {

    @Autowired private SellPlatformContext sellPlatformContext;

    @Override
    public BaseResponse registerApply(@Valid SellPlatformBaseRequest request) {
        SellPlatformApplyService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(),
                SellPlatformServiceType.SELL_APPLY_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.registerApply(request);
    }

    @Override
    public BaseResponse registerFinishAccess(@Valid SellPlatformFinishAccessRequest request) {
        SellPlatformApplyService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_APPLY_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.registerFinishAccess(request);
    }

    @Override
    public BaseResponse registerApplyScene(@Valid SellPlatformApplySceneRequest request) {
        SellPlatformApplyService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_APPLY_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.registerApplyScene(request);
    }

    @Override
    public BaseResponse registerCheck(@Valid SellPlatformBaseRequest request) {
        SellPlatformApplyService applyService = sellPlatformContext.getPlatformService(request.getSellPlatformType(), SellPlatformServiceType.SELL_APPLY_SERVICE);
        if (Objects.isNull(applyService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return applyService.registerCheck(request);
    }
}
