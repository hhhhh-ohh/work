package com.wanmi.sbc.goods.api.request.distributionmatter;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Size;

import lombok.Data;

import java.util.List;

@Data
@Schema
public class DeleteByIdListRequest extends BaseRequest {

    @Schema(description = "id列表")
    @Size(min = 1)
    private List ids;
}
