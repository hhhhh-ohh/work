package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.account.bean.dto.SettleGoodDTO;
import com.wanmi.sbc.account.bean.dto.SettleTradeDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 计算明细
 * Created by hht on 2017/12/6.
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettlementDetailDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 结算id
     */
    @Schema(description = "结算id")
    private String id;

    /**
     * 结算明细id
     */
    @Schema(description = "结算明细id")
    private String settleUuid;

    /**
     * 账期开始时间
     */
    @Schema(description = "账期开始时间")
    private String startTime;

    /**
     * 账期结束时间
     */
    @Schema(description = "账期结束时间")
    private String endTime;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 是否特价
     */
    @Schema(description = "是否特价")
    private boolean isSpecial;

    /**
     * 订单信息
     */
    @Schema(description = "订单信息")
    private SettleTradeDTO settleTrade;

    /**
     * 订单商品信息
     */
    @Schema(description = "订单商品信息")
    private List<SettleGoodDTO> settleGoodList;

    /**
     * 订单和退单是否属于同一个账期
     */
    @Schema(description = "订单和退单是否属于同一个账期")
    private boolean tradeAndReturnInSameSettle;

}
