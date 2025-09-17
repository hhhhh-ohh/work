package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * @author xuyunpeng
 * @className LedgerReceiverRelBindStateRequest
 * @description
 * @date 2022/7/18 10:00 AM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverRelBindStateRequest extends BaseRequest {
    private static final long serialVersionUID = 4374056856248571619L;

    /**
     * 商户id company_info_id
     */
    @Schema(description = "商户id")
    @NotNull
    private Long supplierId;

    /**
     * 接收方店铺id
     */
    @Schema(description = "接收方店铺id")
    @NotEmpty
    private List<Long> receiverStoreIds;
}
