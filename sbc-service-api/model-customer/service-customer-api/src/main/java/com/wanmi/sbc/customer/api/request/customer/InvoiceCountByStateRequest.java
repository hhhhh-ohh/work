package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceCountByStateRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "审核状态")
    private CheckState checkState;
}
