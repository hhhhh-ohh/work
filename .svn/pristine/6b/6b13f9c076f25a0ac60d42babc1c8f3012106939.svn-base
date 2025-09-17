package com.wanmi.sbc.order.api.response.orderinvoice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.order.bean.vo.OrderInvoiceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class OrderInvoiceFindAllResponse extends BasicResponse {

    @Schema(description = "订单开票分页信息")
    MicroServicePage<OrderInvoiceVO> value;
}
