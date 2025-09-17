package com.wanmi.sbc.order.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>订单批量审核请求参数结构</p>
 * Created by of628-wenzhi on 2017-11-28-下午4:49.
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class TradeAuditBatchRequest extends TradeAuditRequest{
    private static final long serialVersionUID = 1L;

    /**
     * 订单号ids
     */
    @Schema(description = "订单号ids")
    private String[] ids;
}
