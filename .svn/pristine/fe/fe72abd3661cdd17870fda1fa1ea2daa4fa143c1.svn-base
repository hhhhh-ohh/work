package com.wanmi.sbc.empower.api.request.pay.gateway;

import com.wanmi.sbc.empower.api.request.pay.PayBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>根据店铺id 获取网关列表request</p>
 * Created by of628-wenzhi on 2018-08-13-下午3:53.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewayByStoreIdRequest extends PayBaseRequest {
    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

}
