package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsPropDetailRelRequest
 * 根据多个SpuID查询属性关联请求对象
 * @author lipeng
 * @dateTime 2018/11/5 上午11:05
 */
@Schema
@Data
public class GoodsPropDetailRelByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 3262831993727237552L;

    @Schema(description = "商品Id")
    private List<String> goodsIds;
}
