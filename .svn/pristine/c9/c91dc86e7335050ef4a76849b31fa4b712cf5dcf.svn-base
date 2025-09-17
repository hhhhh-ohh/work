package com.wanmi.sbc.linkedmall;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.provider.thirdplatformreturn.ThirdPlatformReturnOrderProvider;
import com.wanmi.sbc.order.api.provider.thirdplatformreturn.ThirdPlatformReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.request.linkedmall.ThirdPlatformReturnOrderApplyRequest;
import com.wanmi.sbc.order.api.request.linkedmall.ThirdPlatformReturnOrderReasonRequest;
import com.wanmi.sbc.order.api.response.linkedmall.ThirdPlatformReturnReasonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * linkedMall退货申请
 * Created by dyt on 29/8/2020.
 */
@Tag(name = "LinkedMallReturnOrderController", description = "linkedMall退单 Api")
@RestController
@Validated
@RequestMapping("/return/linkedMall")
public class LinkedMallReturnOrderController {

    @Autowired
    private ThirdPlatformReturnOrderProvider thirdPlatformReturnOrderProvider;

    @Autowired
    private ThirdPlatformReturnOrderQueryProvider thirdPlatformReturnOrderQueryProvider;

    @Operation(summary = "linkedMall退单原因查询")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @GetMapping("/reasons/{rid}")
    public BaseResponse<ThirdPlatformReturnReasonResponse> reasons(@PathVariable String rid) {
        return thirdPlatformReturnOrderQueryProvider.listReturnReason(ThirdPlatformReturnOrderReasonRequest.builder().rid(rid).build());
    }

    @Operation(summary = "linkedMall退单申请")
    @PutMapping("/apply")
    public BaseResponse apply(@RequestBody @Valid ThirdPlatformReturnOrderApplyRequest request) {
        return thirdPlatformReturnOrderProvider.apply(request);
    }
}
