package com.wanmi.sbc.elastic.api.request.orderinvoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: hanwei
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EsOrderInvoiceDeleteRequest extends BaseRequest {

    /**
     * 开票id
     */
    @Schema(description = "开票id")
    private String orderInvoiceId;

}
