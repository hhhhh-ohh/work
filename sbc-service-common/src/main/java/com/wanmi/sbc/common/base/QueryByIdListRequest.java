package com.wanmi.sbc.common.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class QueryByIdListRequest {

    @Schema(description = "id列表")
    private List<String> idList;
}
