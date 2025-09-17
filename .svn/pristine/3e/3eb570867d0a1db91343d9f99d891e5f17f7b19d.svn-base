package com.wanmi.sbc.elastic.api.request.orderinvoice;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author houshuai
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EsOrderInvoiceModifyStateRequest extends BaseRequest {

    /**
     * 开票id列表
     */
    @Schema(description = "开票id列表")
    private List<String> orderInvoiceIds;

    /**
     * 是否开票 1是，0否
     */
    @Schema(description = "是否开票 1是，0否")
    private Integer invoiceState;

}