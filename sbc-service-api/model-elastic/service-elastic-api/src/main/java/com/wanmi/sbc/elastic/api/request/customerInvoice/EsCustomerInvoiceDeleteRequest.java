package com.wanmi.sbc.elastic.api.request.customerInvoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class EsCustomerInvoiceDeleteRequest extends BaseRequest {

    /**
     * 专票ids
     */
    @Schema(description = "专票ids")
    private List<Long> customerInvoiceIds;
}
