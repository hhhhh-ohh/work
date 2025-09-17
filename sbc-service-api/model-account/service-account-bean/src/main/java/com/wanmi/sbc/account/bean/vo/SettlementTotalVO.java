package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.enums.SettleStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema
@Data
public class SettlementTotalVO extends BasicResponse {

    private static final long serialVersionUID = 4798379474821689390L;
    /**
     * 结算金额
     */
    @Schema(description = "结算金额")
    BigDecimal totalAmount;

    /**
     * 结算状态
     */
    @Schema(description = "结算状态")
    private SettleStatus settleStatus;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺id")
    private Long storeId;
}
