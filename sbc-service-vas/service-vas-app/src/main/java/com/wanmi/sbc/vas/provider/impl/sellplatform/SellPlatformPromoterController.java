package com.wanmi.sbc.vas.provider.impl.sellplatform;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformPromoterProvider;
import com.wanmi.sbc.vas.api.request.sellplatform.promoter.SellPlatformPromoterListRequest;
import com.wanmi.sbc.vas.api.response.sellplatform.promoter.SellPlatformPromoterListResponse;
import com.wanmi.sbc.vas.sellplatform.SellPlatformContext;
import com.wanmi.sbc.vas.sellplatform.SellPlatformPromoterService;
import com.wanmi.sbc.vas.sellplatform.SellPlatformServiceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * @author wur
 * @className SellPlatformOrderController
 * @description TODO
 * @date 2022/4/20 13:42
 **/
@Slf4j
@RestController
public class SellPlatformPromoterController implements SellPlatformPromoterProvider {

    @Autowired
    private SellPlatformContext sellPlatformContext;

    @Override
    public BaseResponse<SellPlatformPromoterListResponse> getPromoterList(@Valid SellPlatformPromoterListRequest request) {
        SellPlatformPromoterService platformService = sellPlatformContext.getPlatformService(request.getSellPlatformType(),
                SellPlatformServiceType.SELL_PROMOTER_SERVICE);
        if (Objects.isNull(platformService)) {
            return BaseResponse.SUCCESSFUL();
        }
        return platformService.getPromoterList(request);
    }
}