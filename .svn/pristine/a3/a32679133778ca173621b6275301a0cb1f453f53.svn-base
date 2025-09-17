package com.wanmi.sbc.setting.provider.impl.cancellationreason;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.cancellationreason.CancellationReasonProvider;
import com.wanmi.sbc.setting.api.request.cancellationreason.CancellationReasonModifyRequest;
import com.wanmi.sbc.setting.cancellation.service.CancellationReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author houshuai
 * @date 2022/3/29 16:02
 * @description <p> </p>
 */
@RestController
public class CancellationReasonController implements CancellationReasonProvider {

    @Autowired
    private CancellationReasonService cancellationReasonService;

    @Override
    public BaseResponse modifyCancellationReason(@RequestBody CancellationReasonModifyRequest request) {
        cancellationReasonService.modifyCancellationReason(request);
        return BaseResponse.SUCCESSFUL();
    }
}
