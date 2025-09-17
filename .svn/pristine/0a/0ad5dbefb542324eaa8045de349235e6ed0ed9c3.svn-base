package com.wanmi.sbc.dada.response;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author EDZ
 * @className DaDaAccountResponse
 * @description 达达同城配送配置
 * @date 2021/6/30 14:26
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaDaAccountResponse {
    @Schema(description = "达达账户app_key")
    private String appKey;

    @Schema(description = "达达账户app_secret")
    private String appSecret;

    @Schema(description = "达达账户启用状态 0:未启用1:已启用")
    private Integer status;

    @Schema(description = "达达商户id")
    private String shopNo;

    /**
     * 回调地址
     */
    @Schema(description = "回调地址")
    private String callbackUrl;
}
