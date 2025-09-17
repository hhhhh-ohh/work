package com.wanmi.sbc.order.api.response.paytraderecord;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>支付结果查询response</p>
 * Created by of628-wenzhi on 2018-08-14-下午6:41.
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayResultResponse extends BasicResponse {

    private static final long serialVersionUID = 3761372441698710120L;
    /**
     * 支付状态
     */
    @Schema(description = "支付状态")
    private TradeStatus tradeStatus;
}
