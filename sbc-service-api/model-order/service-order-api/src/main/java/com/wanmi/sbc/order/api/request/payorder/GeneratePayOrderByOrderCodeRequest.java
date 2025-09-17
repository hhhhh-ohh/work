package com.wanmi.sbc.order.api.request.payorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.account.bean.enums.PayType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class GeneratePayOrderByOrderCodeRequest extends BaseRequest {

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderCode;

    /**
     * 会员主键
     */
    @Schema(description = "会员主键")
    private String customerId;

    /**
     * 收款金额
     */
    @Schema(description = "收款金额")
    private BigDecimal payOrderPrice;

    /**
     * 支付类型
     */
    @Schema(description = "支付类型")
    private PayType payType;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long companyInfoId;

    /**
     * 下单时间
     */
    @Schema(description = "下单时间")
    private LocalDateTime orderTime;
}
