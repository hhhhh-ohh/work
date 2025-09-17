package com.wanmi.sbc.trade.request;


import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.Platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class BatchDeliverCheckRequest extends BaseRequest {

    /**
     * 文件后缀
     */
    @Schema(description = "文件后缀")
    private String ext;

    @Schema(description = "店铺Id")
    private Long storeId;

    @Schema(description = "用户Id")
    private String userId;

    @Schema(description = "平台类型")
    private Platform platform;
}
