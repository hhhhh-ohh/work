package com.wanmi.sbc.setting.provider.impl.refundcause;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.refundcause.RefundCauseProvider;
import com.wanmi.sbc.setting.api.request.refundcause.RefundCauseModifyRequest;
import com.wanmi.sbc.setting.refundcause.service.RefundCauseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author houshuai
 * @date 2021/11/16 13:45
 * @description <p> 退款原因编辑 </p>
 */
@RestController
public class RefundCauseController implements RefundCauseProvider {

    @Autowired
    private RefundCauseService refundCauseService;

    /**
     * 退款原因编辑
     * @param request
     * @return
     */
    @Override
    public BaseResponse modifyRefundCause(@RequestBody RefundCauseModifyRequest request) {
        refundCauseService.modifyRefundCause(request);
        return BaseResponse.SUCCESSFUL();
    }
}
