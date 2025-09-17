package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.refundcause.RefundCauseProvider;
import com.wanmi.sbc.setting.api.provider.refundcause.RefundCauseQueryProvider;
import com.wanmi.sbc.setting.api.request.refundcause.RefundCauseModifyRequest;
import com.wanmi.sbc.setting.api.response.refundcause.RefundCauseQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author houshuai
 * @date 2021/11/16 13:53
 * @description <p> 退款原因Controller </p>
 */
@RestController
@Validated
@RequestMapping("/refund")
public class RefundCauseController {

    @Autowired
    private RefundCauseProvider refundCauseProvider;

    @Autowired
    private RefundCauseQueryProvider refundCauseQueryProvider;

    /**
     * 查询全部退款原因
     * @return.
     */
    @GetMapping("/cause-list")
    public BaseResponse<RefundCauseQueryResponse> getRefundCauseList(){
        return refundCauseQueryProvider.findAll();
    }

    /**
     * 退款原因编辑
     * @param request
     * @return
     */
    @PostMapping("/cause-modify")
    public BaseResponse modifyRefundCause(@RequestBody RefundCauseModifyRequest request){
       return refundCauseProvider.modifyRefundCause(request);
    }

}
