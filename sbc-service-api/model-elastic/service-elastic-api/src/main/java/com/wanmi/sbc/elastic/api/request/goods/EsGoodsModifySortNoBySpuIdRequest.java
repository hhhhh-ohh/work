package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EsGoodsModifySortNoBySpuIdRequest extends BaseRequest {

    /**
     * spuId
     */
    @Schema(description = "spuId")
    private String spuId;

    @Schema(description = "排序号")
    private Long sortNo;

}
