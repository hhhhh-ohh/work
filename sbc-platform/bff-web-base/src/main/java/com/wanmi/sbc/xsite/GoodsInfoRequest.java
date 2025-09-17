package com.wanmi.sbc.xsite;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class GoodsInfoRequest extends BaseRequest {

    @Schema(description = "分类名称")
    private Long catName;

    @Schema(description = "第几页")
    private Integer pageNum;

    @Schema(description = "每页几条")
    private Integer pageSize;

    @Schema(description = "查询条件")
    private String q;

    @Schema(description = "类型")
    private Integer type;
}
