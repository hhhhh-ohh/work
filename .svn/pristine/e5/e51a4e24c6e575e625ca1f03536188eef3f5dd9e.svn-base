package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据商品SKU编号查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoPartColsByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -3137678281984258499L;

    /**
     * 批量SKU编号
     */
    @Schema(description = "批量SKU编号")
    private List<String> goodsInfoIds;
}
