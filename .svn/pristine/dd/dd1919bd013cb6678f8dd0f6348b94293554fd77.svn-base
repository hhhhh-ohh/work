package com.wanmi.sbc.crm.api.request.tagdimension;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDimensionBigJsonRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * List
     */
    @Schema(description = "dimensionIdList")
    private List<Long> dimensionIdList;
}
