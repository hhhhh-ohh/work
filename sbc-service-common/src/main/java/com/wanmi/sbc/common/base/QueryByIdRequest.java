package com.wanmi.sbc.common.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class QueryByIdRequest {

    @Schema(description = "id")
    private String id;
}
