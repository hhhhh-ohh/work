package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @className CountByNotWrittenOffOrderResponse
 * @description TODO
 * @author 黄昭
 * @date 2021/9/8 17:15
 **/
@Data
@Builder
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class CountByNotWrittenOffOrderResponse extends BasicResponse {
    private static final long serialVersionUID = 2305212750811014400L;

    @Schema(description = "未核销订单数量")
    private Long count;
}
