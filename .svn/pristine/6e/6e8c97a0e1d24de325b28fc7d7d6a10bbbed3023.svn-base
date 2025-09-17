package com.wanmi.sbc.order.api.response.returnorder;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退单查询可退金额请求结构
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ReturnOrderQueryRefundPriceResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 可退金额
     */
    @Schema(description = "可退金额")
    private BigDecimal refundPrice;
}
