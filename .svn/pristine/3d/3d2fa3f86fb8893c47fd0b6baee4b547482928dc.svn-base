package com.wanmi.sbc.setting.api.provider.cancellationreason;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.cancellationreason.CancellationReasonModifyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author houshuai
 * @date 2021/11/16 13:41
 * @description <p> 注销原因编辑 </p>
 */
@FeignClient(value = "${application.setting.name}", contextId = "CancellationReasonProvider")
public interface CancellationReasonProvider {

    /**
     * 编辑退款原因
     *
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/cancellation-reason/modify")
    BaseResponse modifyCancellationReason(@RequestBody CancellationReasonModifyRequest request);

}
