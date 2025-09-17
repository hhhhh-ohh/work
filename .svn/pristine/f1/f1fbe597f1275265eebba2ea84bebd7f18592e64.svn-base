package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsModifyCateRequest
 * 修改商品分类请求对象
 * @author lipeng
 * @dateTime 2018/11/5 上午10:54
 */
@Schema
@Data
public class GoodsModifyCateRequest extends BaseRequest {

    private static final long serialVersionUID = 2924776922639087908L;

    @Schema(description = "商品id")
    private List<String> goodsIds;

    @Schema(description = "店铺分类id")
    private List<Long> storeCateIds;
}
