package com.wanmi.sbc.empower.api.response.pay.lakala;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author edz
 * @className LakalaShareProfitBaseResponse
 * @description TODO
 * @date 2022/7/14 21:24
 **/
@Data
@Schema
public class LakalaShareProfitBaseResponse<T> implements Serializable {
    @Schema(description = "返回业务代码(000000为成功，其余按照错误信息来定)")
    private String retCode;

    @Schema(description = "返回业务代码描述")
    private String retMsg;

    @Schema(description = "时间戳 yyyyMMddHHmmss")
    private String respTime;

    @Schema(description = "响应序列号")
    private String respId;

    @Schema(description = "sdfds")
    private T respData;
}
