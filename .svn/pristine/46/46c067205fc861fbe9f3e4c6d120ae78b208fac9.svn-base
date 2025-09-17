package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.bean.dto.ReturnCustomerAccountDTO;
import com.wanmi.sbc.order.bean.dto.ReturnOrderDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 退单修改退单价格请求结构
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ReturnOrderOnlineModifyPriceRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 退单信息
     */
    @Schema(description = "退单信息")
    @NotNull
    private ReturnOrderDTO returnOrder;

    /**
     * 退款评论
     */
    @Schema(description = "退款评论")
    private String refundComment;

    /**
     * 客户收款账号id
     */
    @Schema(description = "客户收款账号id")
    private String customerAccountId;

    /**
     * 会员银行账户
     */
    @Schema(description = "会员银行账户")
    private ReturnCustomerAccountDTO customerAccount;

    /**
     * 实退金额
     */
    @Schema(description = "实退金额")
    private BigDecimal actualReturnPrice;

    /**
     * 实退积分
     */
    @Schema(description = "实退积分")
    private Long actualReturnPoints;

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
