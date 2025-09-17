package com.wanmi.sbc.empower.api.request.Ledger.lakala;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseRequest {

    /**
     * 版本号
     */
    @Builder.Default
    private String ver = "1.0.0";

    /**
     * 请求序列号
     */
    @Builder.Default
    private String reqId = UUID.randomUUID().toString().replace("-","");

    /**
     * 时间戳
     */
    @Builder.Default
    private Long timestamp = System.currentTimeMillis();

    /**
     * 请求体
     */
    private LakalaBaseRequest reqData;
}
