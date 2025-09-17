package com.wanmi.sbc.order.api.request.paytraderecord;

import com.wanmi.sbc.order.api.request.OrderBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * @author wur
 * @description 支付流水批量查询请求
 * @date 2021/4/16 19:44
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundPayTradeRecordsRefundRequest extends OrderBaseRequest {

    /**
     * 关联的订单业务id集合
     */
    @Schema(description = "关联的订单业务id集合")
    @NotNull
    private List<String> businessIds;

}
