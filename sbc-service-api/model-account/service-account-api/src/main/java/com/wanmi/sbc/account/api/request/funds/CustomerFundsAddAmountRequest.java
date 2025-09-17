package com.wanmi.sbc.account.api.request.funds;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName CustomerFundsAddAmount
 * @Description 增加账户余额request
 * @Author lvzhenwei
 * @Date 2019/7/16 14:51
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFundsAddAmountRequest extends BaseRequest {

    @NotBlank
    @Schema(description = "会员ID")
    private String customerId;

    @NotNull
    @Schema(description = "余额金额")
    private BigDecimal amount;


    /**
     * 业务编号
     */
    @Schema(description = "业务编号")
    private String businessId;

}
