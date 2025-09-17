package com.wanmi.sbc.order.api.response.payorder;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.PayOrderVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class GeneratePayOrderByOrderCodeResponse extends BasicResponse {

     @Schema(description = "支付单")
     PayOrderVO payOrder;
}
