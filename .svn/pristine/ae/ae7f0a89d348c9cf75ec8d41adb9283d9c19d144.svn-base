package com.wanmi.sbc.order.api.request.payorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;

import com.wanmi.sbc.common.enums.DefaultFlag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class FindByOrderNosRequest extends BaseRequest {

    @Schema(description = "订单编号s")
    List<String> orderNos;

    @Schema(description = "支付状态")
    PayOrderStatus payOrderStatus;

    DefaultFlag queryReceivable;
}
