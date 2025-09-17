package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
@Builder
public class EsGoodsInfoModifyAddedStatusRequest extends BaseRequest {

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态")
    private Integer addedFlag;

    @Schema(description = "商品id列表")
    private List<String> goodsIds;

    @Schema(description = "商品skuId列表")
    private List<String> goodsInfoIds;

    @Schema(description = "商品插件类型")
    private PluginType pluginType;
}
