package com.wanmi.sbc.order.api.request.orderinvoice;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class OrderInvoiceFindByOrderInvoiceIdAndDelFlagRequest extends BaseRequest {

    @Schema(description = "id")
    String id;

    @Schema(description = "删除状态")
    DeleteFlag flag;

}
