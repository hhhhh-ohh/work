package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EsGoodsDeleteByIdsRequest extends BaseRequest {

    /**
     * 需要删除的idList
     */
    @Schema(description = "需要删除的idList")
    private List<String> deleteIds;

}
