package com.wanmi.sbc.setting.api.provider.refundcause;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.refundcause.RefundCauseModifyRequest;
import com.wanmi.sbc.setting.api.response.refundcause.RefundCauseQueryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author houshuai
 * @date 2021/11/16 13:41
 * @description <p> 退款原因编辑 </p>
 */
@FeignClient(value = "${application.setting.name}", contextId = "RefundCauseProvider")
public interface RefundCauseProvider {

    /**
     * 编辑退款原因
     *
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/refund-cause/modify")
    BaseResponse modifyRefundCause(@RequestBody RefundCauseModifyRequest request);

}
