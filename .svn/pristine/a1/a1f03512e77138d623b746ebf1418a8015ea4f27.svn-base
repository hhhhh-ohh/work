package com.wanmi.sbc.empower.api.request.pay.lakala;

import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author edz
 * @className LakalaShareProfitBaseRequest
 * @description 分账基类
 * @date 2022/7/14 21:04
 **/
@Data
@Schema
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LakalaShareProfitBaseRequest implements Serializable {

    @Schema(description = "时间戳 yyyyMMddHHmmss")
    private String reqTime;

    @Schema(description = "版本号")
    @Builder.Default
    private String version = "1.0";

    @Schema(description = "请求序列号")
    @Builder.Default
    private String reqId = UUID.randomUUID().toString().replace("-","");

    @Schema(description = "请求参数")
    private Object reqData;
}
