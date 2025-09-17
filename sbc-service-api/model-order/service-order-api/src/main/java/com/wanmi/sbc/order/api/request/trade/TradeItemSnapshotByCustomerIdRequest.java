package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 获取客户id查询已确认订单商品快照请求结构
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class TradeItemSnapshotByCustomerIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private String customerId;

    @Schema(description = "客户终端token")
    private String terminalToken;
}
