package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品SKU查询视图响应
 * @Author: wur
 * @Date: 2021/6/8 16:21
 */
@Schema
@Data
public class GoodsInfoViewBySkuNoResponse extends BasicResponse {

    private static final long serialVersionUID = 4437161472070174741L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private GoodsInfoVO goodsInfo;

    /**
     * 相关商品SPU信息
     */
    @Schema(description = "相关商品SPU信息")
    private GoodsVO goods;

}
