package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;

/**
 * @author edz
 * @className LedgerReceiverEcApplyRequest
 * @description TODO
 * @date 2022/9/13 15:31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class LedgerReceiverEcApplyRequest implements Serializable {

    @Schema(description = "电子合同EC003申请ID")
    private String ecApplyId;
}
