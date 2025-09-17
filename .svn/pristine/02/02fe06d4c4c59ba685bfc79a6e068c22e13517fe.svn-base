package com.wanmi.sbc.dada.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

/**
 * @author EDZ
 * @className SamecityAccountAddRequest
 * @description 达达同城配送配置
 * @date 2021/6/30 13:58
 **/
@Schema
@Data
public class DaDaAccountRequest extends BaseRequest {

    @Schema(description = "达达账户app_key")
    @NotBlank
    private String appKey;

    @Schema(description = "达达账户app_secret")
    @NotBlank
    private String appSecret;

    @Schema(description = "达达账户启用状态 0:未启用1:已启用")
    @NotBlank
    private Integer status;

    @Schema(description = "达达商户id")
    @NotBlank
    private String shopNo;

    /**
     * 回调地址
     */
    @Schema(description = "回调地址")
    @NotBlank
    @Length(max=128)
    private String callbackUrl;

}
