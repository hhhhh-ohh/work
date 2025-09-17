package com.wanmi.sbc.empower.api.request.pay.gateway;

import com.wanmi.sbc.empower.api.request.pay.PayBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>根据网关id获取单笔交易记录request</p>
 * Created by of628-wenzhi on 2018-08-13-下午3:53.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewayByIdRequest extends PayBaseRequest {

    private static final long serialVersionUID = 7794616103452631785L;
    /**
     * 支付/退款对象id
     */
    @Schema(description = "支付/退款对象id")
    @NotNull
    private Long gatewayId;


    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

}
