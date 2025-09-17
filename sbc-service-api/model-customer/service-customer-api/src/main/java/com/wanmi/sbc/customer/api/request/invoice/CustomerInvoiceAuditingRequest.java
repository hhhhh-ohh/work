package com.wanmi.sbc.customer.api.request.invoice;

import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 会员增票资质-审核Request
 */
@Schema
@Data
public class CustomerInvoiceAuditingRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "增票资质审核状态")
    private CheckState CheckState;

    @Schema(description = "专票ids")
    private List<Long> customerInvoiceIds;

}
