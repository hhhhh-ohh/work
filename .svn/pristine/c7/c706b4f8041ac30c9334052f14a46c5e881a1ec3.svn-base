package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.dto.InvoiceDTO;

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
public class TradeAddInvoiceRequest extends BaseRequest {

    /**
     * 交易id
     */
    @Schema(description = "交易id")
    private String tid;

    /**
     * 发票
     */
    @Schema(description = "发票")
    private InvoiceDTO invoiceDTO;

}
