package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 商品SKU查询视图响应
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsInfoByIdResponse extends GoodsInfoVO implements Serializable {

    private static final long serialVersionUID = -3840943203614377143L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private List<GoodsInfoVO> goodsInfos;
}
