package com.wanmi.sbc.elastic.api.request.distributionmatter;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Size;

import lombok.Data;

import java.util.List;

/**
 * @author houshuai
 * 删除分销素材 参数
 */
@Data
@Schema
public class EsDeleteByIdListRequest extends BaseRequest {

    /**
     * 分销素材ids
     */
    @Schema(description = "id列表")
    @Size(min = 1)
    private List<String> ids;
}