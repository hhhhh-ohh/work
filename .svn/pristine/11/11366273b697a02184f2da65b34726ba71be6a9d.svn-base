package com.wanmi.sbc.empower.api.request.pay;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import java.io.Serializable;

/**
 * @description 获取微信支付V3 - 微信平台证书公钥
 * @author  wur
 * @date: 2022/12/1 16:01
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WxPayV3CertificatesRequest implements Serializable {

    private static final long serialVersionUID = -3092485338458308665L;
    /**
     * 清除缓存重新获取
     */
    @Schema(description = "清除缓存重新获取标识")
    private Boolean cleanCacheFlag = Boolean.FALSE;

    private String serial_no;
}
