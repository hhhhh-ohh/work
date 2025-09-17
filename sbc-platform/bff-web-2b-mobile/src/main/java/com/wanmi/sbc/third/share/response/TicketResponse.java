package com.wanmi.sbc.third.share.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class TicketResponse extends BasicResponse {

    @Schema(description = "字符串")
    private String nonceStr;

    @Schema(description = "时间戳")
    private String timestamp;

    @Schema(description = "加密信息")
    private String signature;

    @Schema(description = "url")
    private String url;

    @Schema(description = "appId")
    private String appId;
}
