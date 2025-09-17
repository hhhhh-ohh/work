package com.wanmi.sbc.goods.api.response.goodsproperty;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPropRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>商品属性列表结果</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyByIdsListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品属性列表结果
     */
    @Schema(description = "商品属性列表结果")
    private GoodsPropRelVO goodsPropRelVO;
}
