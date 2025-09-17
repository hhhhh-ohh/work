package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品-店铺分类关联实体类
 * Created by bail on 2017/11/13.
 */
@Schema
@Data
public class StoreCateGoodsRelaVO extends BasicResponse {

    private static final long serialVersionUID = 5676728115595298423L;

    @Schema(description = "关联id")
    private String id;

    /**
     * 商品标识
     */
    @Schema(description = "商品标识")
    private String goodsId;

    /**
     * 店铺分类标识
     */
    @Schema(description = "店铺分类标识")
    private Long storeCateId;

}

