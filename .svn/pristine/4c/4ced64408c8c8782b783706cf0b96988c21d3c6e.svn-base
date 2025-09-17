package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.order.bean.dto.SupplierDTO;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wur
 * @className GetCommissionTradeParamsRequest
 * @description TODO
 * @date 2023/1/5 10:38
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class GetCommissionTradeParams  extends BaseRequest {

    /**
     * 商家
     */
    @Schema(description = "商家")
    private SupplierDTO supplier;

    @Schema(description = "旧订单商品数据，用于编辑订单的场景")
    private List<TradeItemDTO> oldTradeItems;

    @Schema(description = "加价购商品订单")
    private List<TradeItemDTO> oldPreferential;

    @Schema(description = "开店礼包 - 从快照中获取")
    private DefaultFlag storeBagsFlag = DefaultFlag.NO;
}