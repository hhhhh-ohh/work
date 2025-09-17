package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.cancellationreason.CancellationReasonProvider;
import com.wanmi.sbc.setting.api.provider.cancellationreason.CancellationReasonQueryProvider;
import com.wanmi.sbc.setting.api.request.cancellationreason.CancellationReasonModifyRequest;
import com.wanmi.sbc.setting.api.response.cancellationreason.CancellationReasonQueryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2022/3/29 17:22
 * @description <p> </p>
 */
@Tag(name = "CancellationReasonController", description = "注销原因API")
@RequestMapping("/cancellation")
@RestController
@Validated
public class CancellationReasonController {

    @Autowired
    private CancellationReasonProvider cancellationReasonProvider;

    @Autowired
    private CancellationReasonQueryProvider cancellationReasonQueryProvider;


    /**
     * 查询全部注销原因
     *
     * @return.
     */
    @Operation(summary = "查询全部注销原因")
    @GetMapping("/reason-list")
    public BaseResponse<CancellationReasonQueryResponse> getCancellationReasonList() {
        return cancellationReasonQueryProvider.findAll();
    }

    /**
     * 注销原因编辑
     *
     * @param request
     * @return
     */
    @Operation(summary = "注销原因编辑")
    @PostMapping("/reason-modify")
    public BaseResponse modifyCancellationReason(@Valid @RequestBody CancellationReasonModifyRequest request) {
        return cancellationReasonProvider.modifyCancellationReason(request);
    }


}
