package com.wanmi.sbc.order.api.response.payorder;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.PayOrderVO;

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
public class FindByOrderNosResponse extends BasicResponse {

    @Schema(description = "支付单列表")
    List<PayOrderVO> orders;

}
