package com.wanmi.sbc.order.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.dto.TradeDistributeItemCommissionDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xuyunpeng
 * @className DistributionLedgerRequest
 * @description
 * @date 2022/7/25 4:24 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistributionLedgerRequest extends BaseRequest {

    private static final long serialVersionUID = -201549786337283979L;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    @NotBlank
    private String orderId;

    /**
     * 分销佣金
     */
    @Schema(description = "分销佣金")
    @NotNull
    private BigDecimal commission;


    /**
     * 分销佣金提成信息列表
     */
    private List<TradeDistributeItemCommissionDTO> commissions;
}
