package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品SKU查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Data
@Schema
public class EsDistributorGoodsListQueryRequest extends BaseRequest {

    /**
     * 批量SKU编号
     */
    @Schema(description = "批量SKU编号")
    private List<String> goodsIdList;

    @Schema(description = "分页请求Request")
    private EsGoodsInfoQueryRequest request;

}
