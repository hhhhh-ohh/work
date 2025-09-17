package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.customer.bean.enums.LedgerBindState;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author edz
 * @className DistributionBindStateQueryRequest
 * @description TODO
 * @date 2022/9/14 15:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class DistributionBindStateQueryRequest extends BaseQueryRequest implements Serializable {

    @Schema(description = "商家ID")
    @NotNull
    private String supplierId;

    @Schema(description = "清分账户id")
    private List<String> ledgerAccountId;

    @Schema(description = "分销员名称")
    private String distributionName;

    @Schema(description = "分销员账号")
    private String distributionAccount;

    @Schema(description = "绑定状态")
    private LedgerBindState ledgerBindState;
}
