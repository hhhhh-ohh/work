package com.wanmi.sbc.elastic.api.request.systemresource;

import com.wanmi.sbc.common.base.EsInitRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 素材初始化Request
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsSystemResourceInitRequest extends EsInitRequest {

    private static final long serialVersionUID = -785777317292782176L;

    /**
     * 批量-IDList
     */
    @Schema(description = "批量-IDList")
    private List<Long> idList;

}
