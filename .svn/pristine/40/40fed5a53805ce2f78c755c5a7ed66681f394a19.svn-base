package com.wanmi.sbc.customer.api.request.ledger;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.LedgerFunctionType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @author xuyunpeng
 * @className LalakaAccountAddRequest
 * @description
 * @date 2022/7/9 4:01 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LakalaAccountFunctionRequest extends BaseRequest {
    private static final long serialVersionUID = -5664672678073125749L;

    /**
     * 业务id
     */
    @Schema(description = "业务id")
    private String businessId;

    /**
     * 类型
     */
    @Schema(description = "类型")
    @NotNull
    private LedgerFunctionType type;

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
