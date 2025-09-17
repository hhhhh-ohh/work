package com.wanmi.sbc.order.api.response.refund;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: wanggang
 * @createDate: 2018/12/5 15:41
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class RefundOrderGetSumReturnPriceResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    @Schema(description = "合计退款金额")
    private BigDecimal result;
}
