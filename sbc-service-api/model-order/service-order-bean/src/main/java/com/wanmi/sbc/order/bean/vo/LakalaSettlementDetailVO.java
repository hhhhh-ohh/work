package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.account.bean.vo.LakalaSettleGoodVO;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LakalaSettlementDetailVO extends BasicResponse {
    /**
     * 主键
     */
    @Schema(description = "主键")
    private String id;

    /**
     * 结算明细Id
     */
    @Schema(description = "结算明细Id")
    private String settleUuid;

    @Schema(description = "结算明细类型")
    private StoreType storeType;

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
     * 供应商id
     */
    private Long providerId;

    /**
     * 是否特价
     */
    @Schema(description = "是否特价")
    private boolean isSpecial;

    /**
     * 订单信息
     */
    @Schema(description = "订单信息")
    private LakalaSettleTradeVO settleTrade;

    /**
     * 订单商品信息
     */
    @Schema(description = "订单商品信息")
    private List<LakalaSettleGoodVO> settleGoodList;

    /**
     * 订单和退单是否属于同一个账期
     */
    @Schema(description = "订单和退单是否属于同一个账期")
    private boolean tradeAndReturnInSameSettle;

    /**
     * 退单运费
     */
    @Schema(description = "退单运费")
    private BigDecimal fee;

    /**
     * 分账状态
     */
    @Schema(description = "分账状态")
    private LakalaLedgerStatus lakalaLedgerStatus;

    /**
     * 拉卡拉费率
     */
    @Schema(description = "拉卡拉费率")
    private String lakalaRate;

    /**
     * 拉卡拉手续费
     */
    @Schema(description = "拉卡拉手续费")
    private String lakalaHandlingFee;

    /**
     * 分账失败原因
     */
    private String lakalaLedgerFailReason;
}
