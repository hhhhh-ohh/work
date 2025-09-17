package com.wanmi.sbc.elastic.api.request.orderinvoice;

import com.wanmi.sbc.common.base.EsInitRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsOrderInvoiceInitRequest extends EsInitRequest {

    /**
     * 订单开票IDs
     */
    @Schema(description = "订单开票IDs")
    private List<String> idList;
}