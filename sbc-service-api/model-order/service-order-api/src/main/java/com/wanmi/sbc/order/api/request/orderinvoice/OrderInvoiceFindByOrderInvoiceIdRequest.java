package com.wanmi.sbc.order.api.request.orderinvoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class OrderInvoiceFindByOrderInvoiceIdRequest extends BaseRequest {

    @Schema(description = "id")
    String id;

}
