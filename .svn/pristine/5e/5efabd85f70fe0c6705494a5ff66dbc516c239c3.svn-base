package com.wanmi.sbc.customer.api.request.ledgersupplier;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.customer.bean.enums.LedgerAccountState;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author edz
 * @className supplierApplyRecordRequest
 * @description TODO
 * @date 2022/9/6 18:05
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class SupplierApplyRecordRequest extends BaseQueryRequest implements Serializable {
    @Schema(description = "商家名称")
    private String supplierName;

    @Schema(description = "商家编码")
    private String companyCode;

    @Schema(description = "进件状态")
    private LedgerAccountState applyState;

    @Schema(description = "商家类型")
    @NotNull
    private StoreType storeType;
}
