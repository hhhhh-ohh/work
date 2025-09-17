package com.wanmi.sbc.marketing.api.request.fullreturn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 黄昭
 * @className FullReturnIdRequest
 * @description TODO
 * @date 2022/4/7 15:05
 **/
@Data
@Schema
public class FullReturnIdRequest {

    @Schema(description = "满返活动Id")
    private Long id;

    @Schema(description = "门店Id")
    private String storeId;
}