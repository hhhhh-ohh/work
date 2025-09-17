package com.wanmi.sbc.order.api.request.leadertradedetail;

import com.wanmi.sbc.order.api.request.OrderBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除团长订单请求参数</p>
 * @author Bob
 * @date 2023-08-03 14:16:52
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaderTradeDetailDelByIdRequest extends OrderBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;
}
