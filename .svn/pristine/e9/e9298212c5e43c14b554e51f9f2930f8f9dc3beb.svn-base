package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPropDetailRelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 查询商品属性、图文信息
 *
 * @author yangzhen
 * @date 2020/9/3 11:31
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailProperResponse extends BasicResponse {

    private static final long serialVersionUID = -6854155744135534427L;

    /**
     * 商品图文信息
     */
    @Schema(description = "商品图文信息")
    private String goodsDetail;

    /**
     * 商品属性列表
     */
    @Schema(description = "商品属性列表")
    private List<GoodsPropDetailRelVO> goodsPropDetailRels;
}
