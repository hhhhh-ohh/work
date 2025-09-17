package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-04 11:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradePageCriteriaRequest extends BaseRequest {

    /**
     * 是否是可退单查询
     */
    @Schema(description = "是否是可退单查询",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
    private boolean isReturn;

    /**
     * 分页参数
     */
    @Schema(description = "分页参数")
    private TradeQueryDTO tradePageDTO;

    /**
     * 是否是客户订单查询
     */
    @Schema(description = "是否是客户订单查询",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
    private boolean isCustomerPage = false;

}
