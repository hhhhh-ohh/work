package com.wanmi.sbc.customer.api.response.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuyunpeng
 * @className LedgerReceiverRelBindStateResponse
 * @description
 * @date 2022/7/18 10:28 AM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverRelBindStateResponse implements Serializable {
    private static final long serialVersionUID = -5338115823836021420L;

    /**
     * 未绑定的店铺
     */
    @Schema(description = "未绑定的店铺")
    private List<Long> unBindStores;
}
