package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeOfCustomerInfoReq extends BaseRequest {

    // 客户ID
    private String customerId;

    // 商家ID
    private Long companyId;

    // 店铺ID
    private Long storeId;

    // 父订单ID
    private String parentId;

    // 下单时间
    private LocalDateTime payTime;
}
