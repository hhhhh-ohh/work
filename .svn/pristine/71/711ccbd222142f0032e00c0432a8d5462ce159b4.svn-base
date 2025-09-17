package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 查询供应商子订单的请求参数
 * @Autho caiping
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class FindProviderTradeRequest extends BaseRequest {
    private static final long serialVersionUID = 4478928439116796662L;
    /**
     *商品订单的主订单Id
     */
    @Schema(description = "主订单Id")
    private List<String> parentId;
}
