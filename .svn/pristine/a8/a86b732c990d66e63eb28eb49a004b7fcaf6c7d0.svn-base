package com.wanmi.sbc.empower.api.request.pay.gateway;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>根据支付网关枚举获取网关配置request</p>
 * Created by of628-wenzhi on 2018-08-13-下午4:30.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryWechatConfigByStoreIdRequest extends BaseRequest {

    private static final long serialVersionUID = 4304124969782172682L;

    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;
}
