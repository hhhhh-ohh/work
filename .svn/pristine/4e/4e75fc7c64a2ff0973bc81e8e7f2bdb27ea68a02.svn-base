package com.wanmi.sbc.empower.api.response.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author edz
 * @className LakalaPayBaseResponse
 * @description TODO
 * @date 2022/7/5 20:54
 **/
@Data
@Schema
public class LakalaPayBaseResponse<T> {
    @Schema(description = "返回业务代码")
    private String code;

    @Schema(description = "返回业务代码描述")
    private String msg;

    @Schema(description = "响应时间,格式yyyyMMddHHmmss")
    @JSONField(name = "resp_time")
    private String respTime;

    @Schema(description = "商户号")
    @JSONField(name = "resp_data")
    private T respData;
}
