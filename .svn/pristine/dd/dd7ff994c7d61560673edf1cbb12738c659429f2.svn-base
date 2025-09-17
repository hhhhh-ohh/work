package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 授信支付关联订单参数
 *
 * @author wangchao
 * @since 2021-03-09 10:22:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditOrderRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 订单ID
     */
    @NotBlank
    @Schema(description = "订单ID")
    private String orderId;

    /**
     * 会员ID
     */
    @NotBlank
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
    private DeleteFlag delFlag;
}
