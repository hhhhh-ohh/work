package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午9:47 2019/6/21
 * @Description: 分销单品佣金信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class TradeDistributeItemCommissionDTO extends BaseRequest {

    private static final long serialVersionUID = -5007288350277079667L;

    /**
     * 提成人会员id
     */
    @Schema(description = "提成人会员id")
    private String customerId;

    /**
     * 提成人分销员id
     */
    @Schema(description = "提成人分销员id")
    private String distributorId;

    /**
     * 提成人总佣金
     */
    @Schema(description = "提成人总佣金")
    private BigDecimal commission;

}
