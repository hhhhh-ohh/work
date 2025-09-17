package com.wanmi.sbc.empower.sm.op.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author wur
 * @className OpBaseRequest
 * @description 基础请求类
 * @date 2022/11/17 14:44
 **/
@Data
@Schema
public class OpBaseRequest {

    @Schema(description = "appId", required = true)
    private String appId;

    @Schema(description = "请求时间戳", required = true)
    private Long timestamp;

    @Schema(description = "请求报文", required = true)
    private String data;

    @Schema(description = "签名", required = true)
    private String sign;
}