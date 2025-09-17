package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.refundcause.RefundCauseQueryProvider;
import com.wanmi.sbc.setting.api.response.refundcause.RefundCauseQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

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
    private RefundCauseQueryProvider refundCauseQueryProvider;

    /**
     * 查询全部退款原因
     * @return
     */
    @GetMapping("/cause-list")
    public BaseResponse<RefundCauseQueryResponse> getRefundCauseList(){
        return refundCauseQueryProvider.findAll();
    }

}