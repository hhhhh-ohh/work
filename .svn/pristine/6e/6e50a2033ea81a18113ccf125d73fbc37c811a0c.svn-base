package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.bean.dto.RefundBillDTO;
import com.wanmi.sbc.order.bean.dto.ReturnCustomerAccountDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商家退单线下退款请求结构
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ReturnOrderOfflineRefundForSupplierRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 退单id
     */
    @Schema(description = "退单id")
    @NotBlank
    private String rid;

    /**
     * 会员银行账户
     */
    @Schema(description = "会员银行账户")
    private ReturnCustomerAccountDTO customerAccount;

    /**
     * 退款流水
     */
    @Schema(description = "退款流水")
    @NotNull
    private RefundBillDTO refundBill;

    /**
     * 操作人信息
     */
    @Schema(description = "操作人信息")
    @NotNull
    private Operator operator;


    /**
     * 实付运费
     */
    @Schema(description = "实付运费")
    @Min(value = 0)
    private BigDecimal fee;
}
