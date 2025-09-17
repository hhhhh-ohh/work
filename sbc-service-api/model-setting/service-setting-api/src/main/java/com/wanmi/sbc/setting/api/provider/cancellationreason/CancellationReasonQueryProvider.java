package com.wanmi.sbc.setting.api.provider.cancellationreason;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.cancellationreason.CancellationReasonQueryRequest;
import com.wanmi.sbc.setting.api.request.refundcause.RefundCauseQueryRequest;
import com.wanmi.sbc.setting.api.response.cancellationreason.CancellationReasonDetailResponse;
import com.wanmi.sbc.setting.api.response.cancellationreason.CancellationReasonQueryResponse;
import com.wanmi.sbc.setting.api.response.refundcause.RefundCauseQueryOneResponse;
import com.wanmi.sbc.setting.api.response.refundcause.RefundCauseQueryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author houshuai
 * @date 2021/11/16 11:07
 * @description <p>注销原因查询 </p>
 */
@FeignClient(value = "${application.setting.name}", contextId = "CancellationReasonQueryProvider")
public interface CancellationReasonQueryProvider {

    /**
     * 查询全部退款原因
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/cancellation-reason/find-all")
    BaseResponse<CancellationReasonQueryResponse> findAll();



    /**
     * 查询全部退款原因
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/cancellation-reason/find-by-id")
    BaseResponse<CancellationReasonDetailResponse> findById(@RequestBody CancellationReasonQueryRequest request);
}
