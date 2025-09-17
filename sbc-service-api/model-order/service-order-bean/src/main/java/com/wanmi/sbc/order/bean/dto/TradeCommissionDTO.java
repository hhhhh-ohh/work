package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午10:19 2019/6/19
 * @Description: 分销佣金提成信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class TradeCommissionDTO extends BaseRequest {

    private static final long serialVersionUID = 1746105568499755236L;

    /**
     * 提成人会员id
     */
    @Schema(description = "提成人会员id")
    private String customerId;

    /**
     * 提成人名称
     */
    @Schema(description = "提成人名称")
    private String customerName;

    /**
     * 提成人分销员id
     */
    @Schema(description = "提成人分销员id")
    private String distributorId;

    /**
     * 佣金提成
     */
    @Schema(description = "佣金提成")
    private BigDecimal commission;

}
