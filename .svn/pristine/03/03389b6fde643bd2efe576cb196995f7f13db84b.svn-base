package com.wanmi.sbc.empower.api.request.pay.ali;

import com.wanmi.sbc.empower.api.request.pay.PayRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>支付请求扩展参数结构，主要针对PC，H5端的支付请求</p>
 * Created by of628-wenzhi on 2017-08-05-下午5:08.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class PayExtraRequest extends PayRequest {

    /**
     * 支付成功后的前端回调url
     */
    @Schema(description = "支付成功后的前端回调url")
    private String successUrl;
}
