package com.wanmi.sbc.empower.api.request.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

/**
 * @author edz
 * @className LakalaBasePayrequest
 * @description TODO
 * @date 2022/7/5 20:56
 **/
@Data
@Schema
public class LakalaPayBaseRequest implements Serializable {
    @JSONField(name = "req_time")
    @Schema(description = "请求时间")
    private String reqTime;

    @Schema(description = "版本号")
    private String version = "3.0";

    @JSONField(name = "out_org_code")
    @Schema(description = "外部接入机构号")
    private String outOrgCode;

    @JSONField(name = "req_data")
    @Schema(description = "请求参数")
    private Object reqData;
}
