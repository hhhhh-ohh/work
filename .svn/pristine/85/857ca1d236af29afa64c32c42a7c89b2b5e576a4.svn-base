package com.wanmi.sbc.empower.api.request.pay.lakala;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author edz
 * @className LakalaBasePayrequest
 * @description TODO
 * @date 2022/7/5 20:56
 **/
@Data
@Schema
public class LakalaCasherPayBaseRequest implements Serializable {
    @JSONField(name = "req_time")
    @Schema(description = "请求时间")
    private String reqTime;

    @Schema(description = "版本号")
    private String version = "1.0";

    @JSONField(name = "req_data")
    @Schema(description = "请求参数")
    private Object reqData;
}
