package com.wanmi.sbc.order.api.response.orderinvoice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.OrderInvoiceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class OrderInvoiceFindByOrderInvoiceIdResponse extends BasicResponse {

    @Schema(description = "订单开票信息")
    OrderInvoiceVO orderInvoiceVO;
}
