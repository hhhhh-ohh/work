package com.wanmi.sbc.setting.api.request.refundcause;

import com.wanmi.sbc.common.base.BaseRequest;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundCauseQueryRequest extends BaseRequest {


    /**
     * 退单原因id
     */
    @Schema(description = "退单原因id")
    private String id;
}
