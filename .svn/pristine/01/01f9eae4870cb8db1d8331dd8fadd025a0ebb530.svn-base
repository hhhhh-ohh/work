package com.wanmi.sbc.elastic.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ES商品库删除请求
 * Created by daiyitian on 2017/3/24.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsStandardDeleteByIdsRequest extends BaseRequest {

    /**
     * spuIds
     */
    @Schema(description = "spuIds")
    private List<String> goodsIds;
}
