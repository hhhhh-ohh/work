package com.wanmi.sbc.empower.provider.impl.sellplatform.miniprogramsubscribe;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.empower.api.provider.sellplatform.miniprogramsubscribe.PlatformMiniMsgProvider;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformInitMiniMsgTempRequest;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformSendMiniMsgRequest;
import com.wanmi.sbc.empower.api.response.sellplatform.miniprogramsubscibe.PlatformMiniMsgTempResponse;
import com.wanmi.sbc.empower.sellplatform.PlatformContext;
import com.wanmi.sbc.empower.sellplatform.PlatformMiniMsgService;
import com.wanmi.sbc.empower.sellplatform.PlatformServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
*
 * @description PlatformMiniMsgController
 * @author  xufeng
 * @date: 2022/08/09 19:19
 **/
@RestController
public class PlatformMiniMsgController implements PlatformMiniMsgProvider {

    @Autowired private PlatformContext thirdPlatformContext;

    @Override
    public BaseResponse<List<PlatformMiniMsgTempResponse>> initMiniProgramSubscribeTemplate(PlatformInitMiniMsgTempRequest request) {
        PlatformMiniMsgService miniProgramSubscribeService =
                thirdPlatformContext.getPlatformService(SellPlatformType.MINI_PROGRAM_SUBSCRIBE, PlatformServiceType.MINI_PROGRAM_SUBSCRIBE_SERVICE);
        return miniProgramSubscribeService.initMiniProgramSubscribeTemplate(request);
    }

    @Override
    public BaseResponse sendMiniProgramSubscribeMessage(PlatformSendMiniMsgRequest request) {
        PlatformMiniMsgService miniProgramSubscribeService =
                thirdPlatformContext.getPlatformService(SellPlatformType.MINI_PROGRAM_SUBSCRIBE, PlatformServiceType.MINI_PROGRAM_SUBSCRIBE_SERVICE);
        miniProgramSubscribeService.sendMiniProgramSubscribeMessage(request);
        return BaseResponse.SUCCESSFUL();
    }
}
