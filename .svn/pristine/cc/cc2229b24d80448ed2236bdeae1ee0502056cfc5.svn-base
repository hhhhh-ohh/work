package com.wanmi.sbc.empower.api.request.channel.vop;

import com.wanmi.sbc.empower.api.request.vop.base.VopBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @description VOP订单重起发起支付请求类
 * @author daiyitian
 * @date 2021/5/10 14:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VopOrderRePayRequest extends VopBaseRequest {

    private static final long serialVersionUID = -1L;

    @NotBlank
    @Schema(description = "京东系统订单号")
    private String jdOrderId;
}
