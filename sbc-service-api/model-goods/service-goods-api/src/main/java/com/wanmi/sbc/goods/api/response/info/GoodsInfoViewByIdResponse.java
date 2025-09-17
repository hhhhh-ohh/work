package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品SKU查询视图响应
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
public class GoodsInfoViewByIdResponse extends GoodsInfoViewBaseByIdResponse implements Serializable {
    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private GoodsInfoVO goodsInfo;
}
