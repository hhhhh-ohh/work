package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName TradeTimeOutCancleRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/9 21:20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeTimeOutCancelRequest extends BaseRequest {

    @Schema(description = "订单集合")
    private List<String> tidList;

    @Schema(description = "操作人")
    private Operator operator;
}
