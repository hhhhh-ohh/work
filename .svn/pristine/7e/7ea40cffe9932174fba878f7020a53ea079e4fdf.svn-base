package com.wanmi.sbc.customer.api.request.ledger;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author xuyunpeng
 * @className LedgerRequest
 * @description
 * @date 2022/7/10 9:11 PM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerRequest extends BaseRequest {

    /**
     * 业务id
     */
    @Schema(description = "业务id")
    private String businessId;

    /**
     * 接收方id
     */
    @Schema(description = "接收方id")
    private String receiverId;

    /**
     * 分账绑定关系id
     */
    @Schema(description = "分账绑定关系id")
    private String receiverRelId;

    /**
     * 商户店铺id
     */
    @Schema(description = "商户店铺id")
    private Long supplierStoreId;
}
