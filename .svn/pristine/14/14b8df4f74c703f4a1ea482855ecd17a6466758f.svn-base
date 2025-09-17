package com.wanmi.sbc.order.api.request.orderinvoice;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.account.bean.enums.InvoiceState;
import com.wanmi.sbc.order.bean.dto.OrderInvoiceDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-03 10:24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class OrderInvoiceGenerateRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 开票类
     */
    @Schema(description = "开票类")
    private OrderInvoiceDTO orderInvoiceDTO;

    /**
     * 员工id
     */
    @Schema(description = "员工id")
    private String employeeId;

    /**
     * 发票状态
     */
    @Schema(description = "发票状态")
    private InvoiceState invoiceState;

}
