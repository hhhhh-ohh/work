package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.SettleGoodVO;
import com.wanmi.sbc.account.bean.vo.SettleTradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hht on 2017/12/6.
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettlementDetailVO extends BasicResponse {

    /**
     * 主键
     */
    @Schema(description = "结算id")
    private String id;

    /**
     * 结算明细Id
     */
    @Schema(description = "结算明细Id")
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
    private SettleTradeVO settleTrade;

    /**
     * 订单商品信息
     */
    @Schema(description = "订单商品信息")
    private List<SettleGoodVO> settleGoodList;

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

    @Schema(description = "分账状态")
    private LakalaLedgerStatus lakalaLedgerStatus;

    /**
     * 分账失败原因
     */
    private String lakalaLedgerFailReason;

}
