package com.wanmi.sbc.setting.api.provider.refundcause;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.refundcause.RefundCauseQueryRequest;
import com.wanmi.sbc.setting.api.response.refundcause.RefundCauseQueryOneResponse;
import com.wanmi.sbc.setting.api.response.refundcause.RefundCauseQueryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author houshuai
 * @date 2021/11/16 11:07
 * @description <p>退款原因查询 </p>
 */
@FeignClient(value = "${application.setting.name}", contextId = "RefundCauseQueryProvider")
public interface RefundCauseQueryProvider {

    /**
     * 查询全部退款原因
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/refund-cause/find-all")
    BaseResponse<RefundCauseQueryResponse> findAll();



    /**
     * 查询全部退款原因
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/refund-cause/find-by-id")
    BaseResponse<RefundCauseQueryOneResponse> findById(@RequestBody RefundCauseQueryRequest request);
}
