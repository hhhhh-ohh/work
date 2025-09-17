package com.wanmi.sbc.pointsmall.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author wur
 * @className FetchPointsCouponRequest
 * @description TODO
 * @date 2022/6/30 17:55
 **/
@Data
@Schema(description = "支付请求对象")
public class FetchPointsCouponRequest extends BaseRequest {

    private static final long serialVersionUID = -9011690769518722994L;

    /**
     * 支付密码
     */
    @Schema(description = "支付密码", contentSchema = String.class, required = true)
    @NotNull
    private String payPassword;
}