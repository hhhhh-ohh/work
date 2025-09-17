package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

/**
 * <p>根据父订单号获取订单集合参数</p>
 *
 * @author chenjun
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeListByIdOrPidRequest extends BaseRequest {
    private static final long serialVersionUID = -5608744912093287395L;
    /**
     * 交易id
     */
    @Schema(description = "父交易单id")
    private String parentTid;

    /**
     * 子交易单id
     */
    @Schema(description = "子交易单id")
    private String tid;

    @Schema(description = "是否增加")
    private Boolean isAddFlag;
}
