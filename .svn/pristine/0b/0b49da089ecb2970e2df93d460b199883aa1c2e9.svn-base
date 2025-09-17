package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-05 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ProviderTradeModifyReturnOrderNumByRidRequest extends BaseRequest {

    /**
     * 退单id
     */
    @Schema(description = "退单id")
    private String returnOrderId;

    /**
     * 退单数加减状态
     */
    @Schema(description = "备注")
    private Boolean addFlag;

}
