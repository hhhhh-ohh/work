package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.bean.dto.TradeItemGroupDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-05 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeQueryPurchaseInfoRequest extends BaseRequest {

    /**
     * 交易单分组信息
     */
    @Schema(description = "交易单分组信息")
    private TradeItemGroupDTO tradeItemGroupDTO;

    /**
     * 赠品列表
     */
    @Schema(description = "赠品列表")
    private List<TradeItemDTO> tradeItemList;

}
