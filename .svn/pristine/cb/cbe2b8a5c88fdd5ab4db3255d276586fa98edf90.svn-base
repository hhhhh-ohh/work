package com.wanmi.sbc.order.request;

import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>订单列表导出参数结构</p>
 * Created by of628-wenzhi on 2017-06-09-上午10:33.
 */
@Schema
@Data
public class TradeExportRequest extends TradeQueryDTO {
    // jwt token
    @Schema(description = "jwt token")
    private String token;


    @Schema(description = "o2o")
    private boolean isO2O = false;
}
