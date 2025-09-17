package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.vo.TradeDeliverVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class TradeDeliverRecordResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 发货记录
     */
    @Schema(description = "发货记录列表")
    private List<TradeDeliverVO> tradeDeliver = new ArrayList<>();

    /**
     * 订单总体状态
     */
    @Schema(description = "订单总体状态")
    private String status;

    /**
     * 是否自提订单
     */
    @Schema(description = "是否自提订单")
    private Boolean pickupFlag = false;

    /**
     * 是否拆单
     */
    @Schema(description = "是否拆单")
    private Boolean ordersFlag = false;

    /**
     * 发货状态(供应商子单全部发货为SHIPPED)
     */
    @Schema(description = "发货状态(供应商子单全部发货为SHIPPED)")
    private DeliverStatus deliverStatus;


}
