package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午9:40 2019/4/13
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeReturnOrderNumUpdateRequest extends BaseRequest {

    /**
     * 交易id
     */
    @Schema(description = "交易id")
    private String tid;

    /**
     * 是否增加退单数量 true：增加，false：减少
     */
    @Schema(description = "是否增加退单数量")
    private boolean addFlag;

}
