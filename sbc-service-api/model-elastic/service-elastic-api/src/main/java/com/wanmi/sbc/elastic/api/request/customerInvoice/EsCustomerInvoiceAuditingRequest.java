package com.wanmi.sbc.elastic.api.request.customerInvoice;


import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 会员增票资质-审核Request
 */
@Schema
@Data
public class EsCustomerInvoiceAuditingRequest extends BaseRequest {

    @Schema(description = "增票资质审核状态")
    private CheckState CheckState;

    @Schema(description = "专票ids")
    private List<Long> customerInvoiceIds;
}
